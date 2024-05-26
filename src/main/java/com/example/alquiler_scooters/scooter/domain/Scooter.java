package com.example.alquiler_scooters.scooter.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "scooters")  // Usar minúsculas para evitar problemas con PostgreSQL
@Data
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;  // Cambiado de uuid a id

    @Enumerated(EnumType.STRING)  // Usar enum para controlar los estados
    private EstadoScooter estado;

    private int nivelBateria;

    private String ubicacionActual = "-12.08956475722276, -77.04464072972125";  // Valor predeterminado

    @Lob
    private byte[] qrCodeImage;  // Campo para almacenar la imagen del código QR

    // Enumeración para el estado del scooter
    public enum EstadoScooter {
        DISPONIBLE,
        EN_USO,
        MANTENIMIENTO
    }
}
