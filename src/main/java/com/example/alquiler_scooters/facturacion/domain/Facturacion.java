package com.example.alquiler_scooters.facturacion.domain;

import com.example.alquiler_scooters.metodo_pago.domain.Tarjeta;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.viaje.domain.Viaje;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "facturaciones")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Facturacion.class)
public class Facturacion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Usuario al que pertenece el método de pago

    @ManyToOne
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje; // Viaje al que pertenece la facturación

    @OneToOne
    @JoinColumn(name = "tarjeta_id", nullable = false)
    private Tarjeta tarjeta; // Usuario al que pertenece la tarjeta de pago
}
