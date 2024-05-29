package com.example.alquiler_scooters.estacionamiento.domain;

import com.example.alquiler_scooters.estacionamiento.infrastructure.EstacionamientoRepository;
import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.scooter.infrastructure.ScooterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class EstacionamientoService {
    @Autowired
    private EstacionamientoRepository estacionamientoRepository;

    @Autowired
    private ScooterRepository scooterRepository;

    public List<Estacionamiento> findAll() {
        List<Estacionamiento> estacionamientos = estacionamientoRepository.findAll();
        for (Estacionamiento estacionamiento : estacionamientos) {
            List<Scooter> scooters = scooterRepository.findByUbicacionActual(estacionamiento.getUbicacion());
            estacionamiento.setScooters(scooters);
        }
        return estacionamientos;
    }

    public Optional<Estacionamiento> findById(Long id) {
        Optional<Estacionamiento> estacionamiento = estacionamientoRepository.findById(id);
        estacionamiento.ifPresent(est -> {
            List<Scooter> scooters = scooterRepository.findByUbicacionActual(est.getUbicacion());
            est.setScooters(scooters);
        });
        return estacionamiento;
    }

    public Estacionamiento save(Estacionamiento estacionamiento) {
        return estacionamientoRepository.save(estacionamiento);
    }

    public void deleteById(Long id) {
        estacionamientoRepository.deleteById(id);
    }

    public Estacionamiento updateEstacionamiento(Long id, Estacionamiento estacionamientoDetalles) {
        return estacionamientoRepository.findById(id).map(estacionamiento -> {
            estacionamiento.setNombre(estacionamientoDetalles.getNombre());
            estacionamiento.setUbicacion(estacionamientoDetalles.getUbicacion());
            return estacionamientoRepository.save(estacionamiento);
        }).orElseThrow(() -> new RuntimeException("Estacionamiento no encontrado con id: " + id));
    }

    public String checkScooterInEstacionamiento(Long estacionamientoId, UUID scooterId) {
        Estacionamiento estacionamiento = estacionamientoRepository.findById(estacionamientoId)
                .orElseThrow(() -> new RuntimeException("Estacionamiento no encontrado con id: " + estacionamientoId));

        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new RuntimeException("Scooter no encontrado con id: " + scooterId));

        if (estacionamiento.getUbicacion().equals(scooter.getUbicacionActual())) {
            return "El scooter con id: " + scooterId + " está en el estacionamiento con id: " + estacionamientoId;
        } else {
            return "El scooter con id: " + scooterId + " no está en el estacionamiento con id: " + estacionamientoId;
        }
    }
}