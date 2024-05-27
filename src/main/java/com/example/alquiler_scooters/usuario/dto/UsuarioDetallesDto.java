package com.example.alquiler_scooters.usuario.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioDetallesDto {

    private Long id;

    private String nombre;

    private String email;

    private String telefono;

    private LocalDate fechaRegistro;
}
