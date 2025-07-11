package com.example.weatherapi;

import com.example.weatherapi.entity.WeatherInfo;
import com.example.weatherapi.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class WeatherControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private WeatherService weatherService;

        @Test
        void testGetWeatherByPinAndDate() throws Exception {
                WeatherInfo mockWeather = WeatherInfo.builder()
                                .pincode("110001")
                                .forDate("2025-07-08")
                                .temperature("33°C")
                                .description("clear sky")
                                .build();

                Mockito.when(weatherService.getWeather("110001", "2025-07-08"))
                                .thenReturn(mockWeather);

                mockMvc.perform(get("/api/weather")
                                .param("pin", "110001")
                                .param("forDate", "2025-07-08"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.temperature").value("33°C"))
                                .andExpect(jsonPath("$.description").value("clear sky"));
        }

        @Test
        void testGetWeatherBadRequestWhenPinMissing() throws Exception {
                mockMvc.perform(get("/api/weather")
                                .param("forDate", "2025-07-08"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testGetAllWeather() throws Exception {
                mockMvc.perform(get("/api/weather/all"))
                                .andExpect(status().isOk());
        }
}
