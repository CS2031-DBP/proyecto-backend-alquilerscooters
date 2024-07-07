package com.example.alquiler_scooters.auth.dto;

import com.example.alquiler_scooters.usuario.domain.Role;
import lombok.Data;

@Data
public class AuthRegisterRequest {
    String name;
    String email;
    String password;
    String phone;
    private Role role;

}