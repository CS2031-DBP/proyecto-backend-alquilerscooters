package com.example.alquiler_scooters.usuario.domain;

import com.example.alquiler_scooters.viaje.domain.Viaje;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Email
    private String email;

    @Size(min = 9, max = 9)
    private String telefono;

    private String contrasena;

    private LocalDate fechaRegistro = LocalDate.now();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Viaje> viajes;
}
