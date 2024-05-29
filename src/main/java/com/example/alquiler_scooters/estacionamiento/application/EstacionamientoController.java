package com.example.alquiler_scooters.estacionamiento.application;
import com.example.alquiler_scooters.estacionamiento.domain.Estacionamiento;
import com.example.alquiler_scooters.estacionamiento.domain.EstacionamientoService;
import com.example.alquiler_scooters.estacionamiento.dto.EstacionamientoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/estacionamientos")
public class EstacionamientoController {
    @Autowired
    private EstacionamientoService estacionamientoService;

    @GetMapping
    public List<EstacionamientoDto> getAllEstacionamientos() {
        return estacionamientoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstacionamientoDto> getEstacionamientoById(@PathVariable Long id) {
        Optional<EstacionamientoDto> estacionamiento = estacionamientoService.findById(id);
        if (estacionamiento.isPresent()) {
            return ResponseEntity.ok(estacionamiento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/scooters/{scooterId}")
    public ResponseEntity<String> checkScooterInEstacionamiento(@PathVariable Long id, @PathVariable UUID scooterId) {
        try {
            String mensaje = estacionamientoService.checkScooterInEstacionamiento(id, scooterId);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Estacionamiento> createEstacionamiento(@Valid @RequestBody Estacionamiento estacionamiento) {
        Estacionamiento nuevoEstacionamiento = estacionamientoService.save(estacionamiento);
        return ResponseEntity.ok(nuevoEstacionamiento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstacionamiento(@PathVariable Long id) {
        estacionamientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Estacionamiento> updateEstacionamiento(@PathVariable Long id, @Valid @RequestBody Estacionamiento estacionamientoDetalles) {
        try {
            Estacionamiento estacionamientoActualizado = estacionamientoService.updateEstacionamiento(id, estacionamientoDetalles);
            return ResponseEntity.ok(estacionamientoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}