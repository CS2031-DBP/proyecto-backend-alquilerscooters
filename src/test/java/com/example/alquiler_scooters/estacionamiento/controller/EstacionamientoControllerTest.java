package com.example.alquiler_scooters.estacionamiento.controller;

import com.example.alquiler_scooters.estacionamiento.domain.Estacionamiento;
import com.example.alquiler_scooters.estacionamiento.domain.EstacionamientoService;
import com.example.alquiler_scooters.estacionamiento.dto.EstacionamientoDto;
import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static com.example.alquiler_scooters.usuario.domain.Role.ADMIN;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EstacionamientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstacionamientoService estacionamientoService;

    private Estacionamiento estacionamiento;
    private EstacionamientoDto estacionamientoDto;
    private Scooter scooter;

    @BeforeEach
    public void setUp() {
        estacionamiento = new Estacionamiento();
        estacionamiento.setId(1L);
        estacionamiento.setNombre("Estacionamiento Central");
        estacionamiento.setUbicacion("Calle Falsa 123");

        estacionamientoDto = new EstacionamientoDto();
        estacionamientoDto.setId(1L);
        estacionamientoDto.setNombre("Estacionamiento Central");
        estacionamientoDto.setUbicacion("Calle Falsa 123");

        scooter = new Scooter();
        scooter.setId(UUID.randomUUID());
        scooter.setEstado(Scooter.EstadoScooter.DISPONIBLE);
        scooter.setNivelBateria(100);
        scooter.setUbicacionActual("Calle Falsa 456");
        scooter.setEstacionamiento(estacionamiento);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllEstacionamientos() throws Exception {
        Mockito.when(estacionamientoService.findAll()).thenReturn(Arrays.asList(estacionamientoDto));

        mockMvc.perform(get("/estacionamientos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is(estacionamientoDto.getNombre())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetEstacionamientoById() throws Exception {
        Mockito.when(estacionamientoService.findById(1L)).thenReturn(Optional.of(estacionamientoDto));

        mockMvc.perform(get("/estacionamientos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(estacionamientoDto.getNombre())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetEstacionamientoByIdNotFound() throws Exception {
        Mockito.when(estacionamientoService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/estacionamientos/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateEstacionamiento() throws Exception {
        Mockito.when(estacionamientoService.save(Mockito.any(Estacionamiento.class))).thenReturn(estacionamiento);

        mockMvc.perform(post("/estacionamientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Estacionamiento Central\",\"ubicacion\":\"Calle Falsa 123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(estacionamiento.getNombre())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteEstacionamiento() throws Exception {
        Mockito.doNothing().when(estacionamientoService).deleteById(1L);

        mockMvc.perform(delete("/estacionamientos/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateEstacionamiento() throws Exception {
        Mockito.when(estacionamientoService.updateEstacionamiento(Mockito.eq(1L), Mockito.any(Estacionamiento.class)))
                .thenReturn(estacionamiento);

        mockMvc.perform(patch("/estacionamientos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Estacionamiento Actualizado\",\"ubicacion\":\"Calle Verdadera 456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(estacionamiento.getNombre())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCheckScooterInEstacionamiento() throws Exception {
        UUID scooterId = scooter.getId();
        Mockito.when(estacionamientoService.checkScooterInEstacionamiento(1L, scooterId))
                .thenReturn("Scooter encontrado en el estacionamiento");

        mockMvc.perform(get("/estacionamientos/{id}/scooters/{scooterId}", 1L, scooterId))
                .andExpect(status().isOk())
                .andExpect(content().string("Scooter encontrado en el estacionamiento"));
    }
}