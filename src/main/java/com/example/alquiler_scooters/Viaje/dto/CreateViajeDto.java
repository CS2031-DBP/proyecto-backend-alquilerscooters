package com.example.alquiler_scooters.Viaje.dto;

import lombok.Data;

import java.util.UUID;

@Data

public class CreateViajeDto {
    private Long usuarioId;
    private UUID scooterId;
    private UUID ubicacionInicioId;
    private UUID ubicacionFinId;
}