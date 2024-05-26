package com.example.alquiler_scooters.scooter.domain;

import com.example.alquiler_scooters.scooter.application.QRCodeGenerator;
import com.example.alquiler_scooters.scooter.infrastructure.ScooterRepository;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ScooterService {
    @Autowired
    private ScooterRepository scooterRepository;

    public List<Scooter> findAll() {
        return scooterRepository.findAll();
    }

    public Optional<Scooter> findById(UUID id) {
        return scooterRepository.findById(id);
    }


    public Scooter save(Scooter scooter) {
        Scooter savedScooter = scooterRepository.save(scooter);
        try {
            // Generar el código QR y guardar la imagen en la base de datos
            String qrCodeText = String.valueOf(savedScooter.getId());
            byte[] qrCodeImage = QRCodeGenerator.generateQRCodeImage(qrCodeText, 350, 350);
            savedScooter.setQrCodeImage(qrCodeImage);
            // Guardar nuevamente el scooter actualizado con la imagen QR
            savedScooter = scooterRepository.save(savedScooter);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Could not generate QR Code: " + e.getMessage());
        }

        return savedScooter;
    }

    public void deleteById(UUID id) {
        scooterRepository.deleteById(id);
    }

    public Scooter updateScooter(UUID id, Scooter scooterDetalles) {
        return scooterRepository.findById(id).map(scooter -> {
            if (scooterDetalles.getEstado() != null) {
                scooter.setEstado(scooterDetalles.getEstado());
            }
            if (scooterDetalles.getNivelBateria() != 0) { // Suponiendo que nivelBateria no puede ser 0 inicialmente
                scooter.setNivelBateria(scooterDetalles.getNivelBateria());
            }
            if (scooterDetalles.getUbicacionActual() != null) {
                scooter.setUbicacionActual(scooterDetalles.getUbicacionActual());
            }
            return scooterRepository.save(scooter);
        }).orElseThrow(() -> new RuntimeException("Scooter no encontrado con id: " + id));
    }
}