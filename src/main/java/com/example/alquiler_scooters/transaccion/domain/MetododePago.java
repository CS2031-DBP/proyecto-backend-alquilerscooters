package com.example.alquiler_scooters.transaccion.domain;

import com.example.alquiler_scooters.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MetododePago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private String tokenTarjeta; // Token seguro de la tarjeta de pago

    private String tipoMetodo; // Tipo de método de pago (tarjeta, PayPal, etc.)
}
