package com.example.alquiler_scooters.recompensa.controller;


import com.example.alquiler_scooters.excepciones.ResourceNotFoundException;
import com.example.alquiler_scooters.recompensa.application.RecompensaController;
import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import com.example.alquiler_scooters.recompensa.domain.RecompensaService;
import com.example.alquiler_scooters.recompensa.dto.RecompensaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecompensaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RecompensaService recompensaService;

    @InjectMocks
    private RecompensaController recompensaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetAllRecompensas() {
        List<Recompensa> recompensaList = new ArrayList<>();
        Recompensa recompensa = new Recompensa();
        recompensaList.add(recompensa);

        when(recompensaService.getAllRecompensas()).thenReturn(recompensaList);

        List<RecompensaDTO> result = recompensaController.getAllRecompensas();

        assertFalse(result.isEmpty());
        assertEquals(recompensaList.size(), result.size());
        verify(recompensaService, times(1)).getAllRecompensas();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetRecompensaById() {
        Long id = 1L;
        Recompensa recompensa = new Recompensa();
        recompensa.setId(id);

        when(recompensaService.getRecompensaById(id)).thenReturn(recompensa);

        ResponseEntity<RecompensaDTO> response = recompensaController.getRecompensaById(id);

        assertNotNull(response);
        assertEquals(recompensa.getId(), response.getBody().getId());
        verify(recompensaService, times(1)).getRecompensaById(id);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetRecompensaByIdNotFound() throws Exception {
        when(recompensaService.getRecompensaById(1L)).thenThrow(new ResourceNotFoundException("Recompensa no encontrada con ID: 1"));

        mockMvc.perform(MockMvcRequestBuilders.get("/recompensa/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetRecompensasByUsuarioId() {
        Long usuarioId = 1L;
        List<Recompensa> recompensaList = new ArrayList<>();
        Recompensa recompensa = new Recompensa();
        recompensaList.add(recompensa);

        when(recompensaService.getRecompensasByUsuarioId(usuarioId)).thenReturn(recompensaList);

        List<RecompensaDTO> result = recompensaController.getRecompensasByUsuarioId(usuarioId);

        assertFalse(result.isEmpty());
        assertEquals(recompensaList.size(), result.size());
        verify(recompensaService, times(1)).getRecompensasByUsuarioId(usuarioId);
    }

    @Test

    void testCreateRecompensa() {
        RecompensaDTO recompensaDTO = new RecompensaDTO();
        recompensaDTO.setViajeId(UUID.randomUUID());
        recompensaDTO.setNombre("Recompensa");
        recompensaDTO.setDescripcion("Descripción");

        Recompensa recompensa = new Recompensa();
        recompensa.setId(1L);

        when(recompensaService.createRecompensa(any(UUID.class), any(String.class), any(String.class))).thenReturn(recompensa);

        ResponseEntity<RecompensaDTO> response = recompensaController.createRecompensa(recompensaDTO);

        assertNotNull(response);
        assertEquals(ResponseEntity.ok().body(recompensaDTO), response.getBody());
        verify(recompensaService, times(1)).createRecompensa(any(UUID.class), any(String.class), any(String.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateRecompensaNotFound() {
        RecompensaDTO recompensaDTO = new RecompensaDTO();
        recompensaDTO.setViajeId(UUID.randomUUID());
        recompensaDTO.setNombre("Recompensa");
        recompensaDTO.setDescripcion("Descripción");

        when(recompensaService.createRecompensa(any(UUID.class), any(String.class), any(String.class)))
                .thenThrow(new ResourceNotFoundException("Viaje no encontrado"));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            recompensaController.createRecompensa(recompensaDTO);
        });

        assertNotNull(exception);
        assertEquals("Viaje no encontrado", exception.getMessage());

        verify(recompensaService, times(1)).createRecompensa(any(UUID.class), any(String.class), any(String.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteRecompensa() {
        Long id = 1L;

        doNothing().when(recompensaService).deleteById(id);

        ResponseEntity<Void> response = recompensaController.deleteRecompensa(id);

        assertNotNull(response);
        assertEquals(ResponseEntity.noContent().build(), response);
        verify(recompensaService, times(1)).deleteById(id);
    }

    @Test
    void testDeleteRecompensaNotFound() throws Exception {
        Long id = 1L;

        doThrow(new ResourceNotFoundException("Recompensa no encontrada con ID: " + id))
                .when(recompensaService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/recompensa/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(recompensaService, times(1)).deleteById(id);
    }
}