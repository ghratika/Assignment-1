package com.example.weatherapi.controller;

import com.example.weatherapi.entity.WeatherInfo;
import com.example.weatherapi.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping
    public WeatherInfo getWeather(
            @RequestParam("pin") String pin,
            @RequestParam(value = "forDate", required = false) String forDate) {

        if (forDate == null || forDate.isEmpty()) {
            forDate = LocalDate.now().toString();
        }
        return service.getWeather(pin, forDate);
    }

    @GetMapping("/all")
    public List<WeatherInfo> getAllWeather() {
        return service.getAllWeather();
    }
}
