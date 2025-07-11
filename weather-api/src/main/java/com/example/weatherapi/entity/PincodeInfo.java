package com.example.weatherapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PincodeInfo {
    @Id
    private String pincode;
    private double latitude;
    private double longitude;
}
