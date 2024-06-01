package com.example.alquiler_scooters.viaje.model;

import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.viaje.domain.Viaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ViajeModelTest {

    private Viaje viaje;
    private Scooter scooter;
    private Usuario usuario;
    private Recompensa recompensa;

    @BeforeEach
    public void setUp() {
        viaje = new Viaje();
        viaje.setId(UUID.randomUUID());
        viaje.setHoraInicio(LocalDateTime.now());
        viaje.setHoraFin(LocalDateTime.now().plusHours(1));
        viaje.setPuntoPartida("Point A");
        viaje.setPuntoFin("Point B");
        viaje.setCosto(10.0);
        viaje.setEstado(Viaje.EstadoViaje.ACTIVO);

        scooter = new Scooter();
        scooter.setId(UUID.randomUUID());
        scooter.setEstado(Scooter.EstadoScooter.DISPONIBLE);
        scooter.setNivelBateria(100);
        scooter.setUbicacionActual("Location A");

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("John Doe");
        usuario.setEmail("john.doe@example.com");

        recompensa = new Recompensa();
        recompensa.setId(1L);
        recompensa.setNombre("Recompensa 1");
        recompensa.setDescripcion("Descripción de la recompensa");

        viaje.setScooter(scooter);
        viaje.setUsuario(usuario);
        viaje.setRecompensa(recompensa);
    }

    @Test
    public void testViajeFields() {
        assertEquals(viaje.getHoraInicio().plusHours(1), viaje.getHoraFin());
        assertEquals("Point A", viaje.getPuntoPartida());
        assertEquals("Point B", viaje.getPuntoFin());
        assertEquals(10.0, viaje.getCosto());
        assertEquals(Viaje.EstadoViaje.ACTIVO, viaje.getEstado());
    }

    @Test
    public void testViajeRelations() {
        assertNotNull(viaje.getScooter());
        assertEquals(scooter.getId(), viaje.getScooter().getId());
        assertNotNull(viaje.getUsuario());
        assertEquals(usuario.getNombre(), viaje.getUsuario().getNombre());
        assertNotNull(viaje.getRecompensa());
        assertEquals(recompensa.getNombre(), viaje.getRecompensa().getNombre());
    }
}