package com.example.alquiler_scooters.metodo_pago.domain;

import com.example.alquiler_scooters.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TarjetaDTO {
    private UUID id;
}
