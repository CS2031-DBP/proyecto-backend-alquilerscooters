package com.example.alquiler_scooters.Viaje.infrastructure;

import com.example.alquiler_scooters.Viaje.domain.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {
}
