package com.example.alquiler_scooters.auth.dto;

import lombok.Data;

@Data
public class AuthLoginRequest {
    private String email;
    private String password;
}
