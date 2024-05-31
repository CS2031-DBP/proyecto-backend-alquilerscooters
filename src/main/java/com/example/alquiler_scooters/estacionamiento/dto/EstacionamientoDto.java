package com.example.alquiler_scooters.estacionamiento.dto;

import com.example.alquiler_scooters.scooter.dto.ScooterBasicDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class EstacionamientoDto {
    @NotNull
    private Long id;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String ubicacion;

    private List<ScooterBasicDto> scooters;
}
