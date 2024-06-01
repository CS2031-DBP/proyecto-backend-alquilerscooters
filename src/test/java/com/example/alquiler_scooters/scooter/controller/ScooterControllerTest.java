package com.example.alquiler_scooters.scooter.controller;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.scooter.domain.ScooterService;
import com.example.alquiler_scooters.scooter.dto.ScooterDetailsDto;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScooterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScooterService scooterService;

    private Scooter scooter;
    private ScooterDetailsDto scooterDto;

    @BeforeEach
    public void setUp() {
        scooter = new Scooter();
        scooter.setId(UUID.randomUUID());
        scooter.setEstado(Scooter.EstadoScooter.DISPONIBLE);
        scooter.setNivelBateria(100);
        scooter.setUbicacionActual("Calle Falsa 456");

        scooterDto = new ScooterDetailsDto();
        scooterDto.setId(scooter.getId());
        scooterDto.setEstado(scooter.getEstado());
        scooterDto.setNivelBateria(scooter.getNivelBateria());
        scooterDto.setUbicacionActual(scooter.getUbicacionActual());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllScooters() throws Exception {
        Mockito.when(scooterService.findAll()).thenReturn(Arrays.asList(scooterDto));

        mockMvc.perform(get("/scooters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].ubicacionActual", is(scooterDto.getUbicacionActual())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetScooterById() throws Exception {
        Mockito.when(scooterService.findById(scooter.getId())).thenReturn(Optional.of(scooterDto));

        mockMvc.perform(get("/scooters/{id}", scooter.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ubicacionActual", is(scooterDto.getUbicacionActual())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetScooterByIdNotFound() throws Exception {
        Mockito.when(scooterService.findById(scooter.getId())).thenReturn(Optional.empty());

        mockMvc.perform(get("/scooters/{id}", scooter.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateScooter() throws Exception {
        Mockito.when(scooterService.save(Mockito.any(Scooter.class))).thenReturn(scooter);

        mockMvc.perform(post("/scooters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"estado\":\"DISPONIBLE\",\"nivelBateria\":100,\"ubicacionActual\":\"Calle Falsa 456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ubicacionActual", is(scooter.getUbicacionActual())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteScooter() throws Exception {
        Mockito.when(scooterService.deleteById(scooter.getId())).thenReturn("El scooter con id " + scooter.getId() + " ha sido eliminado.");

        mockMvc.perform(delete("/scooters/{id}", scooter.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("El scooter con id " + scooter.getId() + " ha sido eliminado."));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateScooter() throws Exception {
        Mockito.when(scooterService.updateScooter(Mockito.eq(scooter.getId()), Mockito.any(Scooter.class)))
                .thenReturn(scooter);

        mockMvc.perform(patch("/scooters/{id}", scooter.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"estado\":\"EN_USO\",\"nivelBateria\":80,\"ubicacionActual\":\"Calle Verdadera 456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ubicacionActual", is(scooter.getUbicacionActual())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetScootersWithLowBattery() throws Exception {
        Mockito.when(scooterService.findScootersWithLowBattery()).thenReturn(Arrays.asList(scooterDto));

        mockMvc.perform(get("/scooters/low-battery"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].ubicacionActual", is(scooterDto.getUbicacionActual())));
    }
}