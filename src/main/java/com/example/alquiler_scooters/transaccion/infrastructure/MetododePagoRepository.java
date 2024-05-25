package com.example.alquiler_scooters.transaccion.infrastructure;

import com.example.alquiler_scooters.transaccion.domain.MetododePago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetododePagoRepository extends JpaRepository<MetododePago, Long> {
    List<MetododePago> findByUsuarioId(Long usuarioId);
}