package com.example.alquiler_scooters.transaccion.infrastructure;

import com.example.alquiler_scooters.transaccion.domain.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
}
