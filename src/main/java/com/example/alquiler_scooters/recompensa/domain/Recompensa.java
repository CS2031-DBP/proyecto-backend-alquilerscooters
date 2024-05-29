package com.example.alquiler_scooters.recompensa.domain;

import com.example.alquiler_scooters.estacionamiento.domain.Estacionamiento;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.viaje.domain.Viaje;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Recompensa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje;

    @ManyToOne
    @JoinColumn(name = "estacionamiento_id", nullable = false)
    private Estacionamiento estacionamiento;

    private String nombre;

    private String descripcion;

    private LocalDateTime fecha = LocalDateTime.now();
}
