package com.example.weatherapi;

import com.example.weatherapi.entity.PincodeInfo;
import com.example.weatherapi.entity.WeatherInfo;
import com.example.weatherapi.repository.PincodeRepository;
import com.example.weatherapi.repository.WeatherRepository;
import com.example.weatherapi.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class WeatherServiceTest {

    @Mock
    private PincodeRepository pincodeRepo;

    @Mock
    private WeatherRepository weatherRepo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeatherReturnsCachedData() {
        WeatherInfo cached = WeatherInfo.builder()
                .pincode("110001")
                .forDate("2025-07-07")
                .temperature("30°C")
                .description("clear sky")
                .build();

        when(weatherRepo.findByPincodeAndForDate("110001", "2025-07-07")).thenReturn(cached);

        WeatherInfo result = service.getWeather("110001", "2025-07-07");

        assertEquals("30°C", result.getTemperature());
        assertEquals("clear sky", result.getDescription());
        verify(weatherRepo, times(1)).findByPincodeAndForDate("110001", "2025-07-07");
        verifyNoMoreInteractions(weatherRepo);
    }

    @Test
    void testThrowExceptionWhenPincodeNotFound() {
        String pincode = "999999";
        String date = "2025-07-07";

        // DB returns null for this pincode
        when(pincodeRepo.findById(pincode)).thenReturn(Optional.empty());

        // Mock API to return null (simulate failure)
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class,
                () -> service.getWeather(pincode, date));

        assertEquals("Unable to fetch lat/lon for pincode " + pincode, exception.getMessage());
    }

    @Test
    void testFetchWeatherFromAPIAndSave() {
        PincodeInfo pincodeInfo = PincodeInfo.builder()
                .pincode("110001")
                .latitude(28.6139)
                .longitude(77.2090)
                .build();

        Map<String, Object> mockResponse = new HashMap<>();
        Map<String, Object> main = new HashMap<>();
        main.put("temp", 32.5);
        mockResponse.put("main", main);

        Map<String, String> weather = new HashMap<>();
        weather.put("description", "partly cloudy");
        mockResponse.put("weather", List.of(weather));

        when(pincodeRepo.findById("110001")).thenReturn(Optional.of(pincodeInfo));
        when(weatherRepo.findByPincodeAndForDate("110001", "2025-07-07")).thenReturn(null);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);

        WeatherInfo result = service.getWeather("110001", "2025-07-07");

        assertEquals("32.5°C", result.getTemperature());
        assertEquals("partly cloudy", result.getDescription());
        verify(weatherRepo).save(any(WeatherInfo.class));
    }
}
