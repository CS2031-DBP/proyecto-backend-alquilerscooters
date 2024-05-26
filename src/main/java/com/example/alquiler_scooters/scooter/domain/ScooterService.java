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
        // Generar el código QR
        String qrText = String.valueOf(savedScooter.getId());
        String qrCodePath = "target/qr_codes" + savedScooter.getId() + ".png";
        try {
            QRCodeGenerator.generateQRCode(qrText, 350, 350, qrCodePath);
            savedScooter.setCodigoQR(qrCodePath);
            scooterRepository.save(savedScooter);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el código QR para el scooter con ID: " + savedScooter.getId(), e);
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