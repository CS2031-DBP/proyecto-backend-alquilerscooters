package com.example.alquiler_scooters.estacionamiento.model;

import com.example.alquiler_scooters.estacionamiento.domain.Estacionamiento;
import com.example.alquiler_scooters.scooter.domain.Scooter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EstacionamientoModelTest {

    private Estacionamiento estacionamiento;
    private Scooter scooter;

    @BeforeEach
    public void setUp() {
        estacionamiento = new Estacionamiento();
        estacionamiento.setId(1L);
        estacionamiento.setNombre("Estacionamiento Central");
        estacionamiento.setUbicacion("Calle Principal 123");

        scooter = new Scooter();
        scooter.setId(UUID.randomUUID()); // Suponiendo que el ID de Scooter es de tipo UUID
        scooter.setEstado(Scooter.EstadoScooter.DISPONIBLE);
        scooter.setNivelBateria(100);
        scooter.setUbicacionActual("Calle Falsa 123");
        scooter.setEstacionamiento(estacionamiento);

        List<Scooter> scooters = new ArrayList<>();
        scooters.add(scooter);

        estacionamiento.setScooters(scooters);
    }

    @Test
    public void testEstacionamientoFields() {
        assertEquals(1L, estacionamiento.getId());
        assertEquals("Estacionamiento Central", estacionamiento.getNombre());
        assertEquals("Calle Principal 123", estacionamiento.getUbicacion());
    }

    @Test
    public void testEstacionamientoScooters() {
        List<Scooter> scooters = estacionamiento.getScooters();
        assertNotNull(scooters);
        assertEquals(1, scooters.size());
        assertEquals(scooter, scooters.get(0));
    }

    @Test
    public void testScooterEstacionamiento() {
        assertEquals(estacionamiento, scooter.getEstacionamiento());
    }
}
