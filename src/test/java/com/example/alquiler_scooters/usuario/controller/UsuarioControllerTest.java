package com.example.alquiler_scooters.usuario.controller;

import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.domain.UsuarioService;
import com.example.alquiler_scooters.usuario.dto.UsuarioDetallesDto;
import com.example.alquiler_scooters.viaje.domain.ViajeService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private ViajeService viajeService;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Perez");
        usuario.setEmail("juan.perez@example.com");
        usuario.setTelefono("123456789");
        usuario.setFechaRegistro(LocalDate.now());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllUsuarios() throws Exception {
        UsuarioDetallesDto usuarioDetallesDto = new UsuarioDetallesDto();
        usuarioDetallesDto.setId(usuario.getId());
        usuarioDetallesDto.setNombre(usuario.getNombre());
        usuarioDetallesDto.setEmail(usuario.getEmail());
        usuarioDetallesDto.setTelefono(usuario.getTelefono());
        usuarioDetallesDto.setFechaRegistro(usuario.getFechaRegistro());

        Mockito.when(usuarioService.findAll()).thenReturn(Arrays.asList(usuarioDetallesDto));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is(usuario.getNombre())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetUsuarioById() throws Exception {
        UsuarioDetallesDto usuarioDetallesDto = new UsuarioDetallesDto();
        usuarioDetallesDto.setId(usuario.getId());
        usuarioDetallesDto.setNombre(usuario.getNombre());
        usuarioDetallesDto.setEmail(usuario.getEmail());
        usuarioDetallesDto.setTelefono(usuario.getTelefono());
        usuarioDetallesDto.setFechaRegistro(usuario.getFechaRegistro());

        Mockito.when(usuarioService.findById(usuario.getId())).thenReturn(Optional.of(usuarioDetallesDto));

        mockMvc.perform(get("/usuarios/{id}", usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(usuario.getNombre())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetUsuarioByIdNotFound() throws Exception {
        Mockito.when(usuarioService.findById(usuario.getId())).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuarios/{id}", usuario.getId()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El usuario con ese Id no existe"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void testCreateUsuario() throws Exception {
        Mockito.when(usuarioService.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        String usuarioJson = String.format(
                "{\"nombre\":\"%s\",\"email\":\"%s\",\"telefono\":\"%s\",\"fechaRegistro\":\"%s\"}",
                usuario.getNombre(), usuario.getEmail(), usuario.getTelefono(), usuario.getFechaRegistro().toString()
        );

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(usuario.getNombre())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void testDeleteUsuario() throws Exception {
        Mockito.doNothing().when(usuarioService).deleteById(usuario.getId());

        mockMvc.perform(delete("/usuarios/{id}", usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario con el id " + usuario.getId() + " ha sido eliminado correctamente."));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void testUpdateUsuario() throws Exception {
        Usuario updatedUsuario = new Usuario();
        updatedUsuario.setId(usuario.getId());
        updatedUsuario.setNombre("Maria Gomez");
        updatedUsuario.setEmail("maria.gomez@example.com");
        updatedUsuario.setTelefono("987654321");
        updatedUsuario.setFechaRegistro(LocalDate.now());

        Mockito.when(usuarioService.updateUsuario(Mockito.eq(usuario.getId()), Mockito.any(Usuario.class)))
                .thenReturn(updatedUsuario);

        String updatedUsuarioJson = String.format(
                "{\"nombre\":\"%s\",\"email\":\"%s\",\"telefono\":\"%s\",\"fechaRegistro\":\"%s\"}",
                updatedUsuario.getNombre(), updatedUsuario.getEmail(), updatedUsuario.getTelefono(), updatedUsuario.getFechaRegistro().toString()
        );

        mockMvc.perform(patch("/usuarios/{id}", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUsuarioJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(updatedUsuario.getNombre())));
    }
}
