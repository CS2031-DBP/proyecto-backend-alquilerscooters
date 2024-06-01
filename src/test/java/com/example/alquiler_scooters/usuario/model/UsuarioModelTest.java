package com.example.alquiler_scooters.usuario.model;


import com.example.alquiler_scooters.metodo_pago.domain.Tarjeta;
import com.example.alquiler_scooters.viaje.domain.Viaje;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsuarioModelTest {

    private Usuario usuario;
    private Viaje viaje;
    private Tarjeta tarjeta;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("John Doe");
        usuario.setEmail("john.doe@example.com");
        usuario.setTelefono("123456789");
        usuario.setContrasena("password");
        usuario.setFechaRegistro(LocalDate.now());

        viaje = new Viaje();
        viaje.setId(UUID.randomUUID());
        viaje.setUsuario(usuario);

        tarjeta = new Tarjeta();
        tarjeta.setId(UUID.randomUUID());
        tarjeta.setUsuario(usuario);
        tarjeta.setNumeroTarjeta(123456789L);

        List<Viaje> viajes = new ArrayList<>();
        viajes.add(viaje);
        usuario.setViajes(viajes);

        List<Tarjeta> tarjetas = new ArrayList<>();
        tarjetas.add(tarjeta);
        usuario.setTarjetas(tarjetas);
    }

    @Test
    public void testUsuarioFields() {
        assertEquals(1L, usuario.getId());
        assertEquals("John Doe", usuario.getNombre());
        assertEquals("john.doe@example.com", usuario.getEmail());
        assertEquals("123456789", usuario.getTelefono());
        assertEquals("password", usuario.getContrasena());
        assertEquals(LocalDate.now(), usuario.getFechaRegistro());
    }

    @Test
    public void testUsuarioViajes() {
        List<Viaje> viajes = usuario.getViajes();
        assertNotNull(viajes);
        assertEquals(1, viajes.size());
        assertEquals(viaje, viajes.get(0));
        assertEquals(usuario, viajes.get(0).getUsuario());
    }

    @Test
    public void testUsuarioTarjetas() {
        List<Tarjeta> tarjetas = usuario.getTarjetas();
        assertNotNull(tarjetas);
        assertEquals(1, tarjetas.size());
        assertEquals(tarjeta, tarjetas.get(0));
        assertEquals(usuario, tarjetas.get(0).getUsuario());
    }
}