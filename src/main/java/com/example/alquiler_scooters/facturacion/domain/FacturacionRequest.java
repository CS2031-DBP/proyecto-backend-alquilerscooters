package com.example.alquiler_scooters.facturacion.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class FacturacionRequest {
    private Long usuarioId;
    private UUID viajeId;
    private UUID tarjetaId;
}
