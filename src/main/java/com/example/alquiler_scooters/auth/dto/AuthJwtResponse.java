package com.example.alquiler_scooters.auth.dto;

import lombok.Data;

@Data
public class AuthJwtResponse {
    public String token;
    public Long id;
}
