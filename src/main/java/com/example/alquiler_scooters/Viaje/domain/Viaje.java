package com.example.alquiler_scooters.Viaje.domain;


import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "VIAJE")
@Data
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuarioId;
    private Long scooterId;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private String ubicacionInicio;
    private String ubicacionFin;
    private Double costo;


}
