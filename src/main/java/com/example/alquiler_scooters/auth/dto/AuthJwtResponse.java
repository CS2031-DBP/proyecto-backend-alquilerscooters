package com.example.alquiler_scooters.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthJwtResponse {
    private String token;
}
