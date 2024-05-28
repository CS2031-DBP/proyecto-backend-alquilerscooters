package com.example.alquiler_scooters.scooter.domain;

import com.example.alquiler_scooters.viaje.domain.Viaje;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Random;
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

    private String ubicacionActual;

    @Lob
    private byte[] qrCodeImage;

    @OneToMany(mappedBy = "scooter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Viaje> viajes;

    public enum EstadoScooter {
        DISPONIBLE,
        EN_USO,
        MANTENIMIENTO
    }

    public Scooter() {
        this.ubicacionActual = generateRandomLocation();
    }

    private static String generateRandomLocation() {
        double minLatitude = -12.2;
        double maxLatitude = -12.0;
        double minLongitude = -77.2;
        double maxLongitude = -77.0;
        Random random = new Random();

        double latitude = minLatitude + (maxLatitude - minLatitude) * random.nextDouble();
        double longitude = minLongitude + (maxLongitude - minLongitude) * random.nextDouble();

        return latitude + ", " + longitude;
    }
}