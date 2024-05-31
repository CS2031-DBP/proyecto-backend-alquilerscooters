package com.example.alquiler_scooters.facturacion.domain;

import com.example.alquiler_scooters.metodo_pago.domain.TarjetaDTO;
import com.example.alquiler_scooters.usuario.domain.UsuarioDto;
import com.example.alquiler_scooters.viaje.dto.FacturaViajeDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class FacturacionCreateDTO {
    private UUID id;

    private UsuarioDto usuario;

    private FacturaViajeDTO viaje;

    private TarjetaDTO tarjeta;

}
