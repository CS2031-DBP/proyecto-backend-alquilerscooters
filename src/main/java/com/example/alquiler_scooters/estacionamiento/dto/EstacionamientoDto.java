package com.example.alquiler_scooters.estacionamiento.dto;

import com.example.alquiler_scooters.scooter.dto.ScooterBasicDto;
import lombok.Data;

import java.util.List;

@Data
public class EstacionamientoDto {
    private Long id;
    private String nombre;
    private String ubicacion;
    private List<ScooterBasicDto> scooters;
}
