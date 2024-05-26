package com.example.alquiler_scooters.recompensa.application;


import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import com.example.alquiler_scooters.recompensa.domain.RecompensaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recompensas")
public class RecompensaController {
    @Autowired
    private RecompensaService recompensaService;

    @GetMapping
    public List<Recompensa> getAllRecompensas() {
        return recompensaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recompensa> getRecompensaById(@PathVariable Long id) {
        return recompensaService.findById(id)
                .map(recompensa -> ResponseEntity.ok().body(recompensa))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Recompensa createRecompensa(@RequestBody Recompensa recompensa) {
        return recompensaService.save(recompensa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recompensa> updateRecompensa(@PathVariable Long id, @RequestBody Recompensa recompensaDetails) {
        Recompensa updatedRecompensa = recompensaService.updateRecompensa(id, recompensaDetails);
        return updatedRecompensa != null ? ResponseEntity.ok().body(updatedRecompensa) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Recompensa> partialUpdateRecompensa(@PathVariable Long id, @RequestBody Recompensa recompensaDetails) {
        Recompensa currentRecompensa = recompensaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Recompensa no encontrada con ID " + id));

        // Actualiza solo los campos proporcionados en recompensaDetails
        if (recompensaDetails.getDescripcion() != null) {
            currentRecompensa.setDescripcion(recompensaDetails.getDescripcion());
        }
        if (recompensaDetails.getFecha() != null) {
            currentRecompensa.setFecha(recompensaDetails.getFecha());
        }

        if (recompensaDetails.getId() != null) {
            currentRecompensa.setId(recompensaDetails.getId());
        }

        if (recompensaDetails.getUsuarioId() != null) {
            currentRecompensa.setUsuarioId(recompensaDetails.getUsuarioId());
        }

        Recompensa updatedRecompensa = recompensaService.save(currentRecompensa);
        return ResponseEntity.ok().body(updatedRecompensa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecompensa(@PathVariable Long id) {
        if (recompensaService.findById(id).isPresent()) {
            recompensaService.deleteById(id);
            String message = "Transacción del usuario con el Id:  " + id + " ha sido eliminada.";
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}