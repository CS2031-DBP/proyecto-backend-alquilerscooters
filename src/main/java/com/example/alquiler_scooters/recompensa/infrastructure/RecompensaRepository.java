package com.example.alquiler_scooters.recompensa.infrastructure;

import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecompensaRepository extends JpaRepository<Recompensa, Long> {
    List<Recompensa> findByUsuarioId(Long usuarioId);


}

