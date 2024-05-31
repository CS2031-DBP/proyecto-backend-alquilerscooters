package com.example.alquiler_scooters.scooter.dto;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ScooterDetailsDto {
    @NotNull
    private UUID id;

    @NotNull
    private Scooter.EstadoScooter estado;

    @Min(0)
    @Max(100)
    private int nivelBateria;

    @NotEmpty
    private String ubicacionActual;


}
