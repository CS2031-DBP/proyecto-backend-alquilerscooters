package com.example.alquiler_scooters.facturacion.infrastructure;

import com.example.alquiler_scooters.facturacion.domain.Facturacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FacturacionRepository extends JpaRepository<Facturacion, UUID> {
    List<Facturacion> findByUsuarioId(Long usuarioId);
}
