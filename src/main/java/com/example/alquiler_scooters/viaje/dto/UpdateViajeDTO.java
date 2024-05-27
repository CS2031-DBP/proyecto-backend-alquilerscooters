package com.example.alquiler_scooters.viaje.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateViajeDTO {
    private LocalDateTime horaFin;
    private String puntoFin;
}