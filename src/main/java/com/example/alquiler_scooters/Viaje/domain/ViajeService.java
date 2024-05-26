package com.example.alquiler_scooters.Viaje.domain;


import com.example.alquiler_scooters.Viaje.infrastructure.ViajeRepository;
import com.example.alquiler_scooters.scooter.infrastructure.ScooterRepository;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ScooterRepository scooterRepository;

    public List<Viaje> findAll() {
        return viajeRepository.findAll();
    }

    public Optional<Viaje> findById(Long id) {
        return viajeRepository.findById(id);
    }

    public void deleteById(Long id) {
        viajeRepository.deleteById(id);
    }

    public Viaje save(Viaje viaje) {
        if (viaje.getHoraInicio() != null && viaje.getHoraFin() != null) {
            viaje.setCosto(calcularCosto(viaje.getHoraInicio(), viaje.getHoraFin()));
        }
        return viajeRepository.save(viaje);
    }

    public Viaje updateViaje(Long id, Viaje viajeDetalles) {
        return viajeRepository.findById(id).map(viaje -> {
            viaje.setHoraInicio(viajeDetalles.getHoraInicio());
            viaje.setHoraFin(viajeDetalles.getHoraFin());
            viaje.setUbicacionInicio(viajeDetalles.getUbicacionInicio());
            viaje.setUbicacionFin(viajeDetalles.getUbicacionFin());

            if (viaje.getHoraInicio() != null && viaje.getHoraFin() != null) {
                viaje.setCosto(calcularCosto(viaje.getHoraInicio(), viaje.getHoraFin()));
            }

            return viajeRepository.save(viaje);
        }).orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + id));
    }

    public Viaje startViaje(Long id) {
        return viajeRepository.findById(id).map(viaje -> {
            viaje.setHoraInicio(LocalDateTime.now());
            return viajeRepository.save(viaje);
        }).orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + id));
    }

    public Viaje endViaje(Long id) {
        return viajeRepository.findById(id).map(viaje -> {
            viaje.setHoraFin(LocalDateTime.now());
            if (viaje.getHoraInicio() != null) {
                viaje.setCosto(calcularCosto(viaje.getHoraInicio(), viaje.getHoraFin()));
            }
            return viajeRepository.save(viaje);
        }).orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + id));
    }

    public Double calcularCosto(LocalDateTime horaInicio, LocalDateTime horaFin) {
        long minutos = java.time.Duration.between(horaInicio, horaFin).toMinutes();
        return minutos * 0.10;
    }
}
