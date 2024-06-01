package com.example.alquiler_scooters.estacionamiento.application;
import com.example.alquiler_scooters.estacionamiento.domain.Estacionamiento;
import com.example.alquiler_scooters.estacionamiento.domain.EstacionamientoService;
import com.example.alquiler_scooters.estacionamiento.dto.EstacionamientoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/estacionamientos")
public class EstacionamientoController {
    @Autowired
    private EstacionamientoService estacionamientoService;

    // ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<EstacionamientoDto> getAllEstacionamientos() {
        return estacionamientoService.findAll();
    }

    // ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EstacionamientoDto> getEstacionamientoById(@PathVariable Long id) {
        Optional<EstacionamientoDto> estacionamiento = estacionamientoService.findById(id);
        if (estacionamiento.isPresent()) {
            return ResponseEntity.ok(estacionamiento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}/scooters/{scooterId}")
    public ResponseEntity<String> checkScooterInEstacionamiento(@PathVariable Long id, @PathVariable UUID scooterId) {
        try {
            String mensaje = estacionamientoService.checkScooterInEstacionamiento(id, scooterId);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Estacionamiento> createEstacionamiento(@Valid @RequestBody Estacionamiento estacionamiento) {
        Estacionamiento nuevoEstacionamiento = estacionamientoService.save(estacionamiento);
        return ResponseEntity.ok(nuevoEstacionamiento);
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstacionamiento(@PathVariable Long id) {
        estacionamientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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