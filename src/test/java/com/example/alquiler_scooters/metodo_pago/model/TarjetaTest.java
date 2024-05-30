package com.example.alquiler_scooters.metodo_pago.model;

import com.example.alquiler_scooters.metodo_pago.domain.Tarjeta;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TarjetaTest {

    private Tarjeta tarjeta;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);  // Supón que Usuario usa Long como ID
        usuario.setNombre("John Doe");
        // Configura otros atributos de Usuario si es necesario.

        tarjeta = new Tarjeta();
        tarjeta.setId(UUID.randomUUID());
        tarjeta.setUsuario(usuario);
        tarjeta.setNumeroTarjeta(1234567890123456L);  // Número de tarjeta como Long
        tarjeta.setFechaExpiracion(LocalDateTime.of(2025, 12, 31, 23, 59));
        tarjeta.setCsc("123");
        tarjeta.setTitular("John Doe");
    }

    @Test
    void testGetId() {
        assertNotNull(tarjeta.getId());
    }

    @Test
    void testGetUsuario() {
        assertEquals(usuario, tarjeta.getUsuario());
    }

    @Test
    void testGetNumeroTarjeta() {
        assertEquals(1234567890123456L, tarjeta.getNumeroTarjeta());
    }

    @Test
    void testGetFechaExpiracion() {
        assertEquals(LocalDateTime.of(2025, 12, 31, 23, 59), tarjeta.getFechaExpiracion());
    }

    @Test
    void testGetCsc() {
        assertEquals("123", tarjeta.getCsc());
    }

    @Test
    void testGetTitular() {
        assertEquals("John Doe", tarjeta.getTitular());
    }

    @Test
    void testSetId() {
        UUID newId = UUID.randomUUID();
        tarjeta.setId(newId);
        assertEquals(newId, tarjeta.getId());
    }

    @Test
    void testSetUsuario() {
        Usuario newUsuario = new Usuario();
        newUsuario.setId(2L);  // Supón que Usuario usa Long como ID
        tarjeta.setUsuario(newUsuario);
        assertEquals(newUsuario, tarjeta.getUsuario());
    }

    @Test
    void testSetNumeroTarjeta() {
        Long newNumeroTarjeta = 9876543210987654L;  // Número de tarjeta como Long
        tarjeta.setNumeroTarjeta(newNumeroTarjeta);
        assertEquals(newNumeroTarjeta, tarjeta.getNumeroTarjeta());
    }

    @Test
    void testSetFechaExpiracion() {
        LocalDateTime newFechaExpiracion = LocalDateTime.of(2026, 1, 1, 0, 0);
        tarjeta.setFechaExpiracion(newFechaExpiracion);
        assertEquals(newFechaExpiracion, tarjeta.getFechaExpiracion());
    }

    @Test
    void testSetCsc() {
        String newCsc = "456";
        tarjeta.setCsc(newCsc);
        assertEquals(newCsc, tarjeta.getCsc());
    }

    @Test
    void testSetTitular() {
        String newTitular = "Jane Doe";
        tarjeta.setTitular(newTitular);
        assertEquals(newTitular, tarjeta.getTitular());
    }
}
