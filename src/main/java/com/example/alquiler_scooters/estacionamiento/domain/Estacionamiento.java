package com.example.alquiler_scooters.estacionamiento.domain;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estacionamientos")
@Data

public class Estacionamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String ubicacion;

    @OneToMany(mappedBy = "estacionamiento", cascade = CascadeType.ALL)
    private List<Scooter> scooters = new ArrayList<>();
}

