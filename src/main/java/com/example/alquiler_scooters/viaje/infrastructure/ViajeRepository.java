package com.example.alquiler_scooters.viaje.infrastructure;

import com.example.alquiler_scooters.viaje.domain.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, UUID> {
}
