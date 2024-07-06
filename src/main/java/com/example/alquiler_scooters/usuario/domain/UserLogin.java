package com.example.alquiler_scooters.usuario.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String provider;
    private String providerId;

}
