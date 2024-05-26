package com.example.alquiler_scooters.scooter.infrastructure;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScooterRepository extends JpaRepository<Scooter, UUID> {
}