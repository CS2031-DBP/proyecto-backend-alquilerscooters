package com.example.alquiler_scooters.transaccion.infrastructure;

import com.example.alquiler_scooters.transaccion.domain.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByUsuarioId(Long usuarioId);
}
