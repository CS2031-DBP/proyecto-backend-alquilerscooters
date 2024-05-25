package com.example.alquiler_scooters.scooter.domain;

import com.example.alquiler_scooters.scooter.application.QRCodeGenerator;
import com.example.alquiler_scooters.scooter.infrastructure.ScooterRepository;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ScooterService {
    @Autowired
    private ScooterRepository scooterRepository;

    public Scooter save(Scooter scooter) {
        Scooter savedScooter = scooterRepository.save(scooter);
        // Generar el código QR
        String qrText = "https://yourapp.com/scooter/reserve?id=" + savedScooter.getId();
        String qrCodePath = "path/to/qrcodes/qr_" + savedScooter.getId() + ".png";
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

    public List<Scooter> findAll() {
        return scooterRepository.findAll();
    }

    public Optional<Scooter> findById(Long id) {
        return scooterRepository.findById(id);
    }

    public void deleteById(Long id) {
        scooterRepository.deleteById(id);
    }

    public Scooter updateScooter(Long id, Scooter scooterDetalles) {
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scooter no encontrado"));
        scooter.setEstado(scooterDetalles.getEstado());
        scooter.setNivelBateria(scooterDetalles.getNivelBateria());
        scooter.setUbicacionActual(scooterDetalles.getUbicacionActual());
        return scooterRepository.save(scooter);
    }

    public Scooter actualizarUbicacion(Long id, String nuevaUbicacion) {
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scooter no encontrado"));
        scooter.setUbicacionActual(nuevaUbicacion);
        return scooterRepository.save(scooter);
    }

    public void actualizarUbicaciones() {
        List<Scooter> scooters = scooterRepository.findAll();
        for (Scooter scooter : scooters) {
            String nuevaUbicacion = obtenerNuevaUbicacion();
            scooter.setUbicacionActual(nuevaUbicacion);
            scooterRepository.save(scooter);
        }
    }

    private String obtenerNuevaUbicacion() {
        // Implementa la lógica para obtener la nueva ubicación
        return "00.0000,00.0000"; // Ejemplo de coordenadas GPS
    }
}