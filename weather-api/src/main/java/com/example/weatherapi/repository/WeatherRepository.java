package com.example.weatherapi.repository;

import com.example.weatherapi.entity.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<WeatherInfo, Long> {
    WeatherInfo findByPincodeAndForDate(String pincode, String forDate);
}
