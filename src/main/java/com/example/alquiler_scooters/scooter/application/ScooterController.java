package com.example.alquiler_scooters.scooter.application;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.scooter.domain.ScooterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/scooters")
public class ScooterController {
    @Autowired
    private ScooterService scooterService;

    @GetMapping
    public List<Scooter> getAllScooters() {
        return scooterService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scooter> getScooterById(@PathVariable Long id) {
        Optional<Scooter> scooter = scooterService.findById(id);
        if (scooter.isPresent()) {
            return ResponseEntity.ok(scooter.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Scooter> createScooter(@Valid @RequestBody Scooter scooter) {
        Scooter nuevoScooter = scooterService.save(scooter);
        return ResponseEntity.ok(nuevoScooter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScooter(@PathVariable Long id) {
        scooterService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Scooter> updateScooter(@PathVariable Long id, @Valid @RequestBody Scooter scooterDetalles) {
        try {
            Scooter scooterActualizado = scooterService.updateScooter(id, scooterDetalles);
            return ResponseEntity.ok(scooterActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/ubicacion")
    public ResponseEntity<Scooter> actualizarUbicacion(@PathVariable Long id, @RequestParam String nuevaUbicacion) {
        try {
            Scooter scooterActualizado = scooterService.actualizarUbicacion(id, nuevaUbicacion);
            return ResponseEntity.ok(scooterActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
