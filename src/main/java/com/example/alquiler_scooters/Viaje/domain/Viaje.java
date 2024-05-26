package com.example.alquiler_scooters.Viaje.domain;


import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "viaje")
@Data
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioId;

    @ManyToOne
    @JoinColumn(name = "scooter_id", nullable = false)
    private Scooter scooterId;

    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;

    @OneToOne(cascade = CascadeType.ALL)
    private Scooter ubicacionInicio;

    @OneToOne(cascade = CascadeType.ALL)
    private Scooter ubicacionFin;

    private Double costo;
}
