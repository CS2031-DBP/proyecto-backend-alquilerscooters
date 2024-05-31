package com.example.alquiler_scooters.viaje.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ViajeDTO {
    @NotNull
    private UUID id;

    @NotNull
    private UsuarioSimpleDTO usuario;

    @NotNull
    private ScooterSimpleDTO scooter;

    @NotNull
    private LocalDateTime horaInicio;

    private LocalDateTime horaFin;

    @NotEmpty
    private String puntoPartida;

    @NotEmpty
    private String puntoFin;

    @Positive
    private Double costo;

    @NotEmpty
    private String estado;
}