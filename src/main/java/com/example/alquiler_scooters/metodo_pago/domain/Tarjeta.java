package com.example.alquiler_scooters.metodo_pago.domain;

import com.example.alquiler_scooters.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Usuario al que pertenece el método de pago

    private Long numeroTarjeta; // Número de la Tarjeta de pago

    private LocalDateTime fechaExpiracion; // Fecha de expiración de la Tarjeta de pago

    private String csc; // Código de seguridad de la Tarjeta de pago

    private String titular; // Nombre del titular de la Tarjeta de pago
}
