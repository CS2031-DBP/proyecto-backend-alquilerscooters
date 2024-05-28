package com.example.alquiler_scooters.facturacion.domain;

import com.example.alquiler_scooters.facturacion.infrastructure.FacturacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FacturacionService {

    @Autowired
    private FacturacionRepository facturacionRepository;

    public Facturacion save(Facturacion facturacion) {
        return facturacionRepository.save(facturacion);
    }

    public List<Facturacion> findAll() {
        return facturacionRepository.findAll();
    }

    public Optional<Facturacion> findById(UUID id) {
        return facturacionRepository.findById(id);
    }

    public Facturacion updateFacturacion(UUID id, Facturacion facturacionDetalles) {
        return facturacionRepository.findById(id).map(facturacion -> {
            facturacion.setUsuario(facturacionDetalles.getUsuario());
            facturacion.setViaje(facturacionDetalles.getViaje());
            facturacion.setTarjeta(facturacionDetalles.getTarjeta());
            return facturacionRepository.save(facturacion);
        }).orElseThrow(() -> new RuntimeException("Facturación no encontrada con id: " + id));
    }

    public void deleteById(UUID id) {
        facturacionRepository.deleteById(id);
    }
}