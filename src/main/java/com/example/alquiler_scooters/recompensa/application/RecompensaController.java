package com.example.alquiler_scooters.recompensa.application;


import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import com.example.alquiler_scooters.recompensa.domain.RecompensaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        Recompensa updatedRecompensa = recompensaService.updateRecompensa(id, recompensaDetails);
        return updatedRecompensa != null ? ResponseEntity.ok().body(updatedRecompensa) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecompensa(@PathVariable Long id) {
        if (recompensaService.findById(id).isPresent()) {
            recompensaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
