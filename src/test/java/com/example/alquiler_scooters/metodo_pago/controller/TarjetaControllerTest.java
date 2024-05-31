package com.example.alquiler_scooters.metodo_pago.controller;

import com.example.alquiler_scooters.metodo_pago.domain.Tarjeta;
import com.example.alquiler_scooters.metodo_pago.domain.TarjetaService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TarjetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TarjetaService tarjetaService;

    private Tarjeta tarjeta;

    @BeforeEach
    public void setUp() {
        tarjeta = new Tarjeta();
        tarjeta.setId(UUID.randomUUID());
        tarjeta.setUsuario(null); // Debes asignar un usuario válido si es necesario
        tarjeta.setNumeroTarjeta(1234567812345678L);
        tarjeta.setFechaExpiracion(LocalDateTime.now().plusYears(2));
        tarjeta.setCsc("123");
        tarjeta.setTitular("Juan Perez");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllTarjetas() throws Exception {
        Mockito.when(tarjetaService.findAll()).thenReturn(Arrays.asList(tarjeta));

        mockMvc.perform(get("/tarjetas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titular", is(tarjeta.getTitular())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetTarjetaById() throws Exception {
        Mockito.when(tarjetaService.findById(tarjeta.getId())).thenReturn(Optional.of(tarjeta));

        mockMvc.perform(get("/tarjetas/{id}", tarjeta.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titular", is(tarjeta.getTitular())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetTarjetaByIdNotFound() throws Exception {
        Mockito.when(tarjetaService.findById(tarjeta.getId())).thenReturn(Optional.empty());

        mockMvc.perform(get("/tarjetas/{id}", tarjeta.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateTarjeta() throws Exception {
        Mockito.when(tarjetaService.save(Mockito.any(Tarjeta.class))).thenReturn(tarjeta);

        mockMvc.perform(post("/tarjetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroTarjeta\":1234567812345678,\"fechaExpiracion\":\"2025-05-01T00:00:00\",\"csc\":\"123\",\"titular\":\"Juan Perez\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titular", is(tarjeta.getTitular())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteTarjeta() throws Exception {
        Mockito.doNothing().when(tarjetaService).deleteById(tarjeta.getId());

        mockMvc.perform(delete("/tarjetas/{id}", tarjeta.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateTarjeta() throws Exception {
        Tarjeta updatedTarjeta = new Tarjeta();
        updatedTarjeta.setId(tarjeta.getId());
        updatedTarjeta.setUsuario(null); // Debes asignar un usuario válido si es necesario
        updatedTarjeta.setNumeroTarjeta(8765432187654321L);
        updatedTarjeta.setFechaExpiracion(LocalDateTime.now().plusYears(3));
        updatedTarjeta.setCsc("321");
        updatedTarjeta.setTitular("Maria Gomez");

        Mockito.when(tarjetaService.updateTarjeta(Mockito.eq(tarjeta.getId()), Mockito.any(Tarjeta.class)))
                .thenReturn(updatedTarjeta);

        mockMvc.perform(patch("/tarjetas/{id}", tarjeta.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroTarjeta\":8765432187654321,\"fechaExpiracion\":\"2026-06-01T00:00:00\",\"csc\":\"321\",\"titular\":\"Maria Gomez\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titular", is(updatedTarjeta.getTitular())));
    }
}
