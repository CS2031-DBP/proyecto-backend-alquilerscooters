package com.example.alquiler_scooters.scooter.infrastructure;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScooterRepository extends JpaRepository<Scooter, UUID> {
    @Query("SELECT s FROM Scooter s WHERE s.nivelBateria <= 35 AND (s.estado = 'DISPONIBLE' OR s.estado = 'EN_USO')")
    List<Scooter> findScootersWithLowBattery();
}