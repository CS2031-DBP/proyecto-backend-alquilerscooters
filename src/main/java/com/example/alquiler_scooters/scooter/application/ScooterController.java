package com.example.alquiler_scooters.scooter.application;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.scooter.domain.ScooterService;
import com.example.alquiler_scooters.scooter.dto.ScooterDetailsDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/scooters")
public class ScooterController {
    @Autowired
    ScooterService scooterService;


    // ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ScooterDetailsDto>> getAllScooters() {
        List<ScooterDetailsDto> scooters = scooterService.findAll();
        return ResponseEntity.ok(scooters);
    }

    // ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ScooterDetailsDto> getScooterById(@PathVariable UUID id) {
        Optional<ScooterDetailsDto> scooter = scooterService.findById(id);
        return scooter.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Scooter> createScooter(@Valid @RequestBody Scooter scooter) {
        Scooter nuevoScooter = scooterService.save(scooter);
        return ResponseEntity.ok(nuevoScooter);
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteScooter(@PathVariable UUID id) {
        String result = scooterService.deleteById(id);
        return ResponseEntity.ok(result);
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/low-battery")
    public ResponseEntity<List<ScooterDetailsDto>> getScootersWithLowBattery() {
        List<ScooterDetailsDto> scooters = scooterService.findScootersWithLowBattery();
        return ResponseEntity.ok(scooters);
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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
