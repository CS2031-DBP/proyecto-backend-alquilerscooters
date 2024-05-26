package com.example.alquiler_scooters.recompensa.infrastructure;

import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecompensaRepository extends JpaRepository<Recompensa, Long> {

}
