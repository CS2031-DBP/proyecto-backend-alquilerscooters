package com.example.alquiler_scooters.scooter.domain;

import com.example.alquiler_scooters.scooter.infrastructure.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScooterService { @Autowired
private ScooterRepository scooterRepository;

    public void actualizarUbicaciones() {
        List<Scooter> scooters = scooterRepository.findAll();
        for (Scooter scooter : scooters) {
            String nuevaUbicacion = obtenerNuevaUbicacion(scooter);
            scooter.setUbicacionActual(nuevaUbicacion);
            scooterRepository.save(scooter);
        }
    }

    private String obtenerNuevaUbicacion(Scooter scooter) {
        // Implementa la lógica para obtener la nueva ubicación basada en la ubicación actual
        // Aquí puedes interactuar con un servicio externo o simular el cambio de ubicación
        return "00.0000,00.0000"; // Ejemplo de coordenadas GPS
    }

    public Scooter actualizarUbicacion(Long id, String nuevaUbicacion) {
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scooter no encontrado"));
        scooter.setUbicacionActual(nuevaUbicacion);
        return scooterRepository.save(scooter);
    }

    public List<Scooter> findAll() {
        return scooterRepository.findAll();
    }

    public Optional<Scooter> findById(Long id) {
        return scooterRepository.findById(id);
    }

    public Scooter save(Scooter scooter) {
        return scooterRepository.save(scooter);
    }

    public void deleteById(Long id) {
        scooterRepository.deleteById(id);
    }

    public Scooter updateScooter(Long id, Scooter scooterDetalles) {
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scooter no encontrado"));
        scooter.setCodigoQR(scooterDetalles.getCodigoQR());
        scooter.setEstado(scooterDetalles.getEstado());
        scooter.setNivelBateria(scooterDetalles.getNivelBateria());
        scooter.setUbicacionActual(scooterDetalles.getUbicacionActual());
        return scooterRepository.save(scooter);
    }
}