package com.example.alquiler_scooters.viaje.domain;


import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "viaje")
@Data
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "scooter_id", nullable = false)
    private Scooter scooter;

    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;

    @Column(name = "punto_partida")
    private String puntoPartida;

    @Column(name = "punto_fin")
    private String puntoFin;

    private Double costo;

    @OneToOne(mappedBy = "viaje")
    private Recompensa recompensa;
}
