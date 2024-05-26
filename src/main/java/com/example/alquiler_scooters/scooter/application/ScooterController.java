package com.example.alquiler_scooters.scooter.application;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.scooter.domain.ScooterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/scooters")
public class ScooterController {
    @Autowired
    ScooterService scooterService;

    @GetMapping
    public List<Scooter> getAllScooters() {
        return scooterService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scooter> getScooterById(@PathVariable UUID id) {
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
    public ResponseEntity<Void> deleteScooter(@PathVariable UUID id) {
        scooterService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Scooter> updateScooter(@PathVariable UUID id, @Valid @RequestBody Scooter scooterDetalles) {
        try {
            Scooter scooterActualizado = scooterService.updateScooter(id, scooterDetalles);
            return ResponseEntity.ok(scooterActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
