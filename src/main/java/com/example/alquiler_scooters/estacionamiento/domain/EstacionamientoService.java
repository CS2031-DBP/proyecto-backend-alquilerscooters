package com.example.alquiler_scooters.estacionamiento.domain;

import com.example.alquiler_scooters.estacionamiento.infrastructure.EstacionamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstacionamientoService {

    @Autowired
    private EstacionamientoRepository estacionamientoRepository;

    public List<Estacionamiento> findAll() {
        return estacionamientoRepository.findAll();
    }

    public Optional<Estacionamiento> findById(Long id) {
        return estacionamientoRepository.findById(id);
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
}
