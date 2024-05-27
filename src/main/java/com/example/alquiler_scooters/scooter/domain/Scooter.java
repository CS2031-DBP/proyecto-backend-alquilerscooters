package com.example.alquiler_scooters.scooter.domain;

import com.example.alquiler_scooters.viaje.domain.Viaje;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "scooters")
@Data
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EstadoScooter estado;

    private int nivelBateria;

    private String ubicacionActual; // "-12.08956475722276, -77.04464072972125"

    @Lob
    private byte[] qrCodeImage;

    @OneToMany(mappedBy = "scooter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Viaje> viajes;

    public enum EstadoScooter {
        DISPONIBLE,
        EN_USO,
        MANTENIMIENTO
    }
}
