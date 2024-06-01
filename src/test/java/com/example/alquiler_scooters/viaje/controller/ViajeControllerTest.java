package com.example.alquiler_scooters.viaje.controller;

import com.example.alquiler_scooters.viaje.domain.ViajeService;
import com.example.alquiler_scooters.viaje.dto.ViajeDTO;
import com.example.alquiler_scooters.viaje.exceptions.NoViajesFoundException;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ViajeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ViajeService viajeService;

    private ViajeDTO viajeDTO;

    @BeforeEach
    public void setUp() {
        viajeDTO = new ViajeDTO();
        viajeDTO.setId(UUID.randomUUID());
        viajeDTO.setHoraInicio(LocalDateTime.now().minusHours(1));
        viajeDTO.setHoraFin(LocalDateTime.now());
        viajeDTO.setPuntoPartida("Punto A");
        viajeDTO.setPuntoFin("Punto B");
        viajeDTO.setCosto(10.0);
        viajeDTO.setEstado("ACTIVO");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllViajes() throws Exception {
        Mockito.when(viajeService.findAll()).thenReturn(Arrays.asList(viajeDTO));

        mockMvc.perform(get("/viajes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].puntoPartida", is(viajeDTO.getPuntoPartida())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetViajeById() throws Exception {
        Mockito.when(viajeService.findById(viajeDTO.getId())).thenReturn(Optional.of(viajeDTO));

        mockMvc.perform(get("/viajes/{id}", viajeDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puntoPartida", is(viajeDTO.getPuntoPartida())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetViajeByIdNotFound() throws Exception {
        Mockito.when(viajeService.findById(viajeDTO.getId())).thenReturn(Optional.empty());

        mockMvc.perform(get("/viajes/{id}", viajeDTO.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetViajesByUsuarioId() throws Exception {
        Mockito.when(viajeService.findByUsuarioId(1L)).thenReturn(Arrays.asList(viajeDTO));

        mockMvc.perform(get("/viajes/usuario/{usuarioId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].puntoPartida", is(viajeDTO.getPuntoPartida())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetViajesByUsuarioIdNotFound() throws Exception {
        Mockito.when(viajeService.findByUsuarioId(1L)).thenThrow(new NoViajesFoundException("No tiene viajes registrados este id: 1"));

        mockMvc.perform(get("/viajes/usuario/{usuarioId}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No tiene viajes registrados este id: 1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateViaje() throws Exception {
        String viajeJson = String.format(
                "{\"id\":\"%s\",\"horaInicio\":\"%s\",\"horaFin\":\"%s\",\"puntoPartida\":\"%s\",\"puntoFin\":\"%s\",\"costo\":%f,\"estado\":\"%s\"}",
                viajeDTO.getId(), viajeDTO.getHoraInicio(), viajeDTO.getHoraFin(), viajeDTO.getPuntoPartida(), viajeDTO.getPuntoFin(), viajeDTO.getCosto(), viajeDTO.getEstado()
        );

        Mockito.when(viajeService.save(Mockito.any(ViajeDTO.class))).thenReturn("Viaje creado con éxito con el ID: " + viajeDTO.getId());

        mockMvc.perform(post("/viajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(viajeJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Viaje creado con éxito con el ID: " + viajeDTO.getId()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testFinalizarViaje() throws Exception {
        Mockito.when(viajeService.finalizarViaje(viajeDTO.getId())).thenReturn("Viaje finalizado correctamente.");

        mockMvc.perform(patch("/viajes/{id}", viajeDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Viaje finalizado correctamente."));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteViaje() throws Exception {
        Mockito.doNothing().when(viajeService).deleteById(viajeDTO.getId());

        mockMvc.perform(delete("/viajes/{id}", viajeDTO.getId()))
                .andExpect(status().isNoContent());
    }
}
