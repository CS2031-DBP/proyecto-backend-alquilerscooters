package com.example.alquiler_scooters.metodo_pago.infrastructure;

import com.example.alquiler_scooters.metodo_pago.domain.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TarjetaRepository extends JpaRepository<Tarjeta, UUID> {
}