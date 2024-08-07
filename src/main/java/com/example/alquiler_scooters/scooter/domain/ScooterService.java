package com.example.alquiler_scooters.scooter.domain;

import com.example.alquiler_scooters.config.GPSUtils;
import com.example.alquiler_scooters.scooter.application.GeneradorCodigosQR;
import com.example.alquiler_scooters.scooter.dto.ScooterDetailsDto;
import com.example.alquiler_scooters.scooter.infrastructure.ScooterRepository;
import com.google.zxing.WriterException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class ScooterService {
    @Autowired
    private ScooterRepository scooterRepository;

    @Autowired
    private ModelMapper mapper;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void startLocationSimulation() {
        scheduler.scheduleAtFixedRate(this::updateScooterLocations, 0, 5, TimeUnit.SECONDS);
    }

    private void updateScooterLocations() {
        List<Scooter> scooters = scooterRepository.findAll();
        for (Scooter scooter : scooters) {
            if (scooter.getEstado() == Scooter.EstadoScooter.EN_USO) {
                // Actualizar ubicación
                String newLocation = GPSUtils.updateLocation(scooter.getUbicacionActual());
                scooter.setUbicacionActual(newLocation);

                // Disminuir nivel de batería
                int newBatteryLevel = scooter.getNivelBateria() - 1;
                scooter.setNivelBateria(Math.max(newBatteryLevel, 0)); // Asegurarse de que no baje de 0

                scooterRepository.save(scooter);
            }
        }
    }

    private ScooterDetailsDto convertToDto(Scooter scooter) {
        return mapper.map(scooter, ScooterDetailsDto.class);
    }

    public List<ScooterDetailsDto> findAll() {
        List<Scooter> scooters = scooterRepository.findAll();
        return scooters.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<ScooterDetailsDto> findById(UUID id) {
        return scooterRepository.findById(id)
                .map(this::convertToDto);
    }


    public Scooter save(Scooter scooter) {
        Scooter savedScooter = scooterRepository.save(scooter);
        try {
            // Generar el código QR y guardar la imagen en la base de datos
            String qrCodeText = String.valueOf(savedScooter.getId());
            byte[] qrCodeImage = GeneradorCodigosQR.generateQRCodeImage(qrCodeText, 350, 350);
            savedScooter.setQrCodeImage(qrCodeImage);
            // Guardar nuevamente el scooter actualizado con la imagen QR
            savedScooter = scooterRepository.save(savedScooter);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Could not generate QR Code: " + e.getMessage());
        }

        return savedScooter;
    }

    public String deleteById(UUID id) {
        scooterRepository.deleteById(id);
        return "El scooter con id " + id + " ha sido eliminado.";
    }

    @Transactional
    public List<ScooterDetailsDto> findScootersWithLowBattery() {
        List<Scooter> scooters = scooterRepository.findScootersWithLowBattery();
        return scooters.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScooterDetailsDto> findScootersByEstadoDisponible() {
        List<Scooter> scooters = scooterRepository.findScootersByEstadoDisponible();
        return scooters.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Scooter updateScooter(UUID id, Scooter scooterDetalles) {
        return scooterRepository.findById(id).map(scooter -> {
            if (scooterDetalles.getEstado() != null) {
                scooter.setEstado(scooterDetalles.getEstado());
            }
            if (scooterDetalles.getNivelBateria() != 0) {
                scooter.setNivelBateria(scooterDetalles.getNivelBateria());
            }
            if (scooterDetalles.getUbicacionActual() != null) {
                scooter.setUbicacionActual(scooterDetalles.getUbicacionActual());
            }
            return scooterRepository.save(scooter);
        }).orElseThrow(() -> new RuntimeException("Scooter no encontrado con id: " + id));
    }
}