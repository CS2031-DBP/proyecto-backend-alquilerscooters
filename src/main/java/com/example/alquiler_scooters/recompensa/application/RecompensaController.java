package com.example.alquiler_scooters.recompensa.application;


import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import com.example.alquiler_scooters.recompensa.domain.RecompensaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recompensas")
public class RecompensaController {
    private final RecompensaService recompensaService;

    @Autowired
    public RecompensaController(RecompensaService recompensaService) {
        this.recompensaService = recompensaService;
    }

    @GetMapping
    public ResponseEntity<List<Recompensa>> obtenerTodasLasRecompensas() {
        List<Recompensa> recompensas = recompensaService.obtenerTodasLasRecompensas();
        return new ResponseEntity<>(recompensas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Recompensa> guardarRecompensa(@RequestBody Recompensa recompensa) {
        Recompensa nuevaRecompensa = recompensaService.guardarRecompensa(recompensa);
        return new ResponseEntity<>(nuevaRecompensa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recompensa> actualizarRecompensaCompleta(@PathVariable Long id, @RequestBody Recompensa recompensaDetalles) {
        Optional<Recompensa> recompensaOptional = recompensaService.obtenerRecompensaPorId(id);
        if (recompensaOptional.isPresent()) {
            Recompensa recompensa = recompensaOptional.get();
            recompensa.setUsuarioId(recompensaDetalles.getUsuarioId());
            recompensa.setDescripcion(recompensaDetalles.getDescripcion());
            recompensa.setFecha(recompensaDetalles.getFecha());
            Recompensa recompensaActualizada = recompensaService.guardarRecompensa(recompensa);
            return new ResponseEntity<>(recompensaActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Recompensa> actualizarRecompensa(@PathVariable Long id, @RequestBody Recompensa recompensaDetalles) {
        Optional<Recompensa> recompensaOptional = recompensaService.obtenerRecompensaPorId(id);
        if (recompensaOptional.isPresent()) {
            Recompensa recompensa = recompensaOptional.get();

            // Actualizar los campos solo si los valores no son nulos
            if (recompensaDetalles.getUsuarioId() != null) {
                recompensa.setUsuarioId(recompensaDetalles.getUsuarioId());
            }
            if (recompensaDetalles.getDescripcion() != null) {
                recompensa.setDescripcion(recompensaDetalles.getDescripcion());
            }
            if (recompensaDetalles.getFecha() != null) {
                recompensa.setFecha(recompensaDetalles.getFecha());
            }

            Recompensa recompensaActualizada = recompensaService.guardarRecompensa(recompensa);
            return new ResponseEntity<>(recompensaActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecompensa(@PathVariable Long id) {
        recompensaService.eliminarRecompensa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
