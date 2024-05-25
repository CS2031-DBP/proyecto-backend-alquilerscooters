package com.example.alquiler_scooters.transaccion.domain;

import com.example.alquiler_scooters.Viaje.domain.Viaje;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    private Long viajeId;

    private Double monto;

    private LocalDateTime fecha;
}
