package com.example.alquiler_scooters.scooter.dto;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import lombok.Data;

import java.util.UUID;

@Data
public class ScooterDetailsDto {
    private UUID id;

    private Scooter.EstadoScooter estado;

    private int nivelBateria;

    private String ubicacionActual;


}
