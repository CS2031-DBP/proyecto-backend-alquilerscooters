package com.example.alquiler_scooters.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioDetallesDto {
    @NotNull
    private Long id;

    @NotEmpty
    private String nombre;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String telefono;

    @NotNull
    private LocalDate fechaRegistro;
}
