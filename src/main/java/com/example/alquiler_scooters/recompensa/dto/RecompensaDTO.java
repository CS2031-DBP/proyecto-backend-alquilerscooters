package com.example.alquiler_scooters.recompensa.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecompensaDTO {
    @NotNull
    private Long id;
    @NotNull
    private Long usuarioId;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;

}
