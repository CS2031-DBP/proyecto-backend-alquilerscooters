package com.example.alquiler_scooters.Viaje.application;

import com.example.alquiler_scooters.Viaje.domain.Viaje;
import com.example.alquiler_scooters.Viaje.domain.ViajeService;
import com.example.alquiler_scooters.Viaje.infrastructure.ViajeRepository;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/viajes")
public class ViajeController {

    @Autowired
    private ViajeService viajeService;

    @GetMapping
    public List<Viaje> getAllViajes() {
        return viajeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viaje> getViajeById(@PathVariable Long id) {
        Optional<Viaje> viaje = viajeService.findById(id);
        if (viaje.isPresent()) {
            return ResponseEntity.ok(viaje.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Viaje> createViaje(@Valid @RequestBody Viaje viaje) {
        Viaje nuevoViaje = viajeService.save(viaje);
        return ResponseEntity.ok(nuevoViaje);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViaje(@PathVariable Long id) {
        viajeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Viaje> updateViaje(@PathVariable Long id, @Valid @RequestBody Viaje viajeDetalles) {
        try {
            Viaje viajeActualizado = viajeService.updateViaje(id, viajeDetalles);
            return ResponseEntity.ok(viajeActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    }

