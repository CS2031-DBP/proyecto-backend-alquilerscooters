package com.example.alquiler_scooters.facturacion.controller;


import com.example.alquiler_scooters.facturacion.application.FacturacionController;
import com.example.alquiler_scooters.facturacion.domain.*;
import com.example.alquiler_scooters.metodo_pago.domain.Tarjeta;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.domain.UsuarioDto;
import com.example.alquiler_scooters.viaje.domain.Viaje;
import com.example.alquiler_scooters.viaje.dto.FacturaViajeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class FacturacionControllerTest {

    @Mock
    private FacturacionService facturacionService;

    @InjectMocks
    private FacturacionController facturacionController;

    private Facturacion facturacion;
    private FacturacionRequest facturacionRequest;
    private FacturacionDto facturacionDto;

    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mapper = new ModelMapper();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("John Doe");

        Viaje viaje = new Viaje();
        viaje.setId(UUID.randomUUID());
        viaje.setPuntoPartida("Point A");
        viaje.setPuntoFin("Point B");
        viaje.setCosto(10.0);

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(UUID.randomUUID());
        tarjeta.setNumeroTarjeta(1234567890123456L);
        tarjeta.setTitular("John Doe");

        facturacion = new Facturacion();
        facturacion.setId(UUID.randomUUID());
        facturacion.setUsuario(usuario);
        facturacion.setViaje(viaje);
        facturacion.setTarjeta(tarjeta);

        facturacionRequest = new FacturacionRequest();
        facturacionRequest.setUsuarioId(usuario.getId());
        facturacionRequest.setViajeId(viaje.getId());
        facturacionRequest.setTarjetaId(tarjeta.getId());

        facturacionDto = new FacturacionDto();
        facturacionDto.setId(facturacion.getId());
        facturacionDto.setUsuario(mapper.map(usuario, UsuarioDto.class));
        facturacionDto.setViaje(mapper.map(viaje, FacturaViajeDTO.class));
    }

    @Test
    void testGetAllFacturaciones() {
        List<FacturacionDto> facturaciones = new ArrayList<>();
        facturaciones.add(facturacionDto);

        when(facturacionService.findAll()).thenReturn(facturaciones);

        ResponseEntity<List<FacturacionDto>> response = facturacionController.getAllFacturaciones();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(facturaciones, response.getBody());
        verify(facturacionService, times(1)).findAll();
    }

    @Test
    void testGetFacturacionById() {
        when(facturacionService.findById(facturacion.getId())).thenReturn(Optional.of(facturacion));

        ResponseEntity<Facturacion> response = facturacionController.getFacturacionById(facturacion.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(facturacion, response.getBody());
        verify(facturacionService, times(1)).findById(facturacion.getId());
    }

    @Test
    void testCreateFacturacion() {
        when(facturacionService.save(any(FacturacionRequest.class))).thenReturn(facturacion);

        ResponseEntity<Facturacion> response = facturacionController.createFacturacion(facturacionRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(facturacion, response.getBody());
        verify(facturacionService, times(1)).save(any(FacturacionRequest.class));
    }

    @Test
    void testUpdateFacturacion() {
        when(facturacionService.updateFacturacion(eq(facturacion.getId()), any(Facturacion.class)))
                .thenReturn(facturacion);

        ResponseEntity<Facturacion> response = facturacionController.updateFacturacion(facturacion.getId(), facturacion);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(facturacion, response.getBody());
        verify(facturacionService, times(1)).updateFacturacion(eq(facturacion.getId()), any(Facturacion.class));
    }

    @Test
    void testDeleteFacturacion() {
        doNothing().when(facturacionService).deleteById(facturacion.getId());

        ResponseEntity<String> response = facturacionController.deleteTransaccion(facturacion.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("ha sido eliminada"));
        verify(facturacionService, times(1)).deleteById(facturacion.getId());
    }
}