package com.example.alquiler_scooters.Viaje.domain;


import com.example.alquiler_scooters.Viaje.infrastructure.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

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

    private Double calcularCosto(LocalDateTime horaInicio, LocalDateTime horaFin) {
        long minutos = java.time.Duration.between(horaInicio, horaFin).toMinutes();
        // Asumiendo un costo de $0.50 por minuto
        return minutos * 0.50;
    }


}
