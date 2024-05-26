package com.example.alquiler_scooters.transaccion.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TRANSACCION")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    private Long viajeId;

    private Double monto;

    private LocalDateTime fecha;

}
