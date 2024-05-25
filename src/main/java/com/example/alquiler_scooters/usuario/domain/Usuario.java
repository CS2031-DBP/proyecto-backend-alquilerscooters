package com.example.alquiler_scooters.usuario.domain;

import com.example.alquiler_scooters.Viaje.domain.Viaje;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "USUARIOS")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Email
    private String email;

    private String telefono;

    private String contrasena;

    private LocalDate fechaRegistro = LocalDate.now();

}
