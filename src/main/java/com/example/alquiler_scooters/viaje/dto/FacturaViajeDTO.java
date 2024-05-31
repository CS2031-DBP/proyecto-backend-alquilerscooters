package com.example.alquiler_scooters.viaje.dto;

import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.viaje.domain.Viaje;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class FacturaViajeDTO {
    private UUID id;
    private LocalDateTime horaFin;
    private Double costo;
}
