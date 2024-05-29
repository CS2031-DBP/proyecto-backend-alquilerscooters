package com.example.alquiler_scooters.estacionamiento.domain;

import com.example.alquiler_scooters.estacionamiento.infrastructure.EstacionamientoRepository;
import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.scooter.dto.ScooterBasicDto;
import com.example.alquiler_scooters.estacionamiento.dto.EstacionamientoDto;
import com.example.alquiler_scooters.scooter.infrastructure.ScooterRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class EstacionamientoService {
    @Autowired
    private EstacionamientoRepository estacionamientoRepository;

    @Autowired
    private ScooterRepository scooterRepository;

    @Autowired
    private ModelMapper mapper;

    public List<EstacionamientoDto> findAll() {
        List<Estacionamiento> estacionamientos = estacionamientoRepository.findAll();
        return estacionamientos.stream().map(estacionamiento -> {
            List<Scooter> scooters = scooterRepository.findByUbicacionActual(estacionamiento.getUbicacion());
            List<ScooterBasicDto> scooterDtos = scooters.stream()
                    .map(scooter -> mapper.map(scooter, ScooterBasicDto.class))
                    .collect(Collectors.toList());
            EstacionamientoDto dto = mapper.map(estacionamiento, EstacionamientoDto.class);
            dto.setScooters(scooterDtos);
            return dto;
        }).collect(Collectors.toList());
    }

    public Optional<EstacionamientoDto> findById(Long id) {
        Optional<Estacionamiento> estacionamiento = estacionamientoRepository.findById(id);
        return estacionamiento.map(est -> {
            List<Scooter> scooters = scooterRepository.findByUbicacionActual(est.getUbicacion());
            List<ScooterBasicDto> scooterDtos = scooters.stream()
                    .map(scooter -> mapper.map(scooter, ScooterBasicDto.class))
                    .collect(Collectors.toList());
            EstacionamientoDto dto = mapper.map(est, EstacionamientoDto.class);
            dto.setScooters(scooterDtos);
            return dto;
        });
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