package com.example.alquiler_scooters.recompensa.domain;

import com.example.alquiler_scooters.estacionamiento.domain.Estacionamiento;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.viaje.domain.Viaje;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Recompensa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "viaje_id")
    private Viaje viaje;

    @ManyToOne
    @JoinColumn(name = "estacionamiento_id")
    private Estacionamiento estacionamiento;

    @NotNull
    private LocalDateTime fecha = LocalDateTime.now();

    private String nombre;

    private String descripcion;
}

