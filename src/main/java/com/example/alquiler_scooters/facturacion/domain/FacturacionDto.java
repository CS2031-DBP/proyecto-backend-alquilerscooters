package com.example.alquiler_scooters.facturacion.domain;

import com.example.alquiler_scooters.usuario.domain.UsuarioDto;
import com.example.alquiler_scooters.viaje.dto.FacturaViajeDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class FacturacionDto {
    private UUID id;

    private UsuarioDto Usuario;

    private FacturaViajeDTO Viaje;
}
