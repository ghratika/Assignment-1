package com.example.weatherapi.service;

import com.example.weatherapi.entity.PincodeInfo;
import com.example.weatherapi.entity.WeatherInfo;
import com.example.weatherapi.repository.PincodeRepository;
import com.example.weatherapi.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final PincodeRepository pincodeRepo;
    private final WeatherRepository weatherRepo;
    private final RestTemplate restTemplate;

    @Value("${openweather.api.key}")
    private String apiKey;

    public WeatherService(PincodeRepository pincodeRepo,
            WeatherRepository weatherRepo,
            RestTemplate restTemplate) {
        this.pincodeRepo = pincodeRepo;
        this.weatherRepo = weatherRepo;
        this.restTemplate = restTemplate;
    }

    public WeatherInfo getWeather(String pincode, String forDate) {
        WeatherInfo cachedWeather = weatherRepo.findByPincodeAndForDate(pincode, forDate);
        if (cachedWeather != null) {
            return cachedWeather;
        }

        PincodeInfo pincodeInfo = pincodeRepo.findById(pincode).orElseGet(() -> {
            PincodeInfo info = fetchLatLonFromGeocodingAPI(pincode);
            return pincodeRepo.save(info);
        });

        WeatherInfo weather = fetchWeatherFromAPI(pincodeInfo, forDate);
        weatherRepo.save(weather);
        return weather;
    }

    private PincodeInfo fetchLatLonFromGeocodingAPI(String pincode) {
        String geoUrl = String.format(
                "https://api.openweathermap.org/geo/1.0/zip?zip=%s,IN&appid=%s",
                pincode, apiKey);

        Map<String, Object> response = restTemplate.getForObject(geoUrl, Map.class);

        if (response == null || !response.containsKey("lat") || !response.containsKey("lon")) {
            throw new RuntimeException("Unable to fetch lat/lon for pincode " + pincode);
        }

        double lat = Double.parseDouble(response.get("lat").toString());
        double lon = Double.parseDouble(response.get("lon").toString());

        return PincodeInfo.builder()
                .pincode(pincode)
                .latitude(lat)
                .longitude(lon)
                .build();
    }

    private WeatherInfo fetchWeatherFromAPI(PincodeInfo pincodeInfo, String forDate) {
        String weatherUrl = String.format(
                "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric",
                pincodeInfo.getLatitude(), pincodeInfo.getLongitude(), apiKey);

        Map<String, Object> response = restTemplate.getForObject(weatherUrl, Map.class);

        if (response == null || !response.containsKey("main")) {
            throw new RuntimeException("Unable to fetch weather for lat/lon");
        }

        Map<String, Object> main = (Map<String, Object>) response.get("main");
        String description = ((List<Map<String, Object>>) response.get("weather"))
                .get(0).get("description").toString();

        return WeatherInfo.builder()
                .pincode(pincodeInfo.getPincode())
                .forDate(forDate)
                .temperature(main.get("temp").toString() + "°C")
                .description(description)
                .build();
    }

    public List<WeatherInfo> getAllWeather() {
        return weatherRepo.findAll();
    }
}
