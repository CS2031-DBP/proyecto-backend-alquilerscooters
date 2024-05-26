package com.example.alquiler_scooters.estacionamiento.infrastructure;

import com.example.alquiler_scooters.estacionamiento.domain.Estacionamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionamientoRepository extends JpaRepository<Estacionamiento, Long> {
}