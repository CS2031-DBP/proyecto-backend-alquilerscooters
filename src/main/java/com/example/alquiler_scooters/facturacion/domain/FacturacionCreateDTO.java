package com.example.alquiler_scooters.facturacion.domain;

import com.example.alquiler_scooters.metodo_pago.domain.TarjetaDTO;
import com.example.alquiler_scooters.usuario.domain.UsuarioDto;
import com.example.alquiler_scooters.viaje.dto.FacturaViajeDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class FacturacionCreateDTO {
    @NotNull
    private UUID id;

    @NotNull
    private UsuarioDto usuario;

    @NotNull
    private FacturaViajeDTO viaje;

    @NotNull
    private TarjetaDTO tarjeta;

}
