package com.example.alquiler_scooters.usuario.domain;


import lombok.Data;

@Data
public class UsuarioDto {
    private Long id;

    private String nombre;

    private String email;

    private String telefono;

}
