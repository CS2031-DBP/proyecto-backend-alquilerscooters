package com.example.alquiler_scooters.scooter.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "SCOOTERS")
@Data
public class Scooter {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String codigoQR;

private String estado;

private int nivelBateria;

private String ubicacionActual;
}
