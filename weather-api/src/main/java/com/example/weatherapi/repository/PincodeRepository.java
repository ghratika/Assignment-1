package com.example.weatherapi.repository;

import com.example.weatherapi.entity.PincodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PincodeRepository extends JpaRepository<PincodeInfo, String> {
}
