package com.example.alquiler_scooters.estacionamiento.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estacionamientos")
@Data

public class Estacionamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String ubicacion;
}
