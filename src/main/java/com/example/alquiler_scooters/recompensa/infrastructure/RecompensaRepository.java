package com.example.alquiler_scooters.recompensa.infrastructure;

import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecompensaRepository extends JpaRepository<Recompensa, Long> {
    List<Recompensa> findByUsuarioId(Long usuarioId);
}

