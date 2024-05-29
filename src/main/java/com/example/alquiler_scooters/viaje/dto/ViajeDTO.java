package com.example.alquiler_scooters.viaje.dto;

import com.example.alquiler_scooters.scooter.dto.ScooterDetailsDto;
import com.example.alquiler_scooters.usuario.dto.UsuarioDetallesDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ViajeDTO {
    private UUID id;
    private UsuarioDetallesDto usuario;
    private ScooterDetailsDto scooter;
    private LocalDateTime horaInicio = LocalDateTime.now();
    private LocalDateTime horaFin;
    private String puntoPartida;
    private String puntoFin;
    private Double costo;
}