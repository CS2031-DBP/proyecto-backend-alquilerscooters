package com.example.alquiler_scooters.Viaje.application;

import com.example.alquiler_scooters.Viaje.domain.Viaje;
import com.example.alquiler_scooters.Viaje.domain.ViajeService;
import com.example.alquiler_scooters.Viaje.dto.CreateViajeDto;
import com.example.alquiler_scooters.Viaje.infrastructure.ViajeRepository;
import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.scooter.infrastructure.ScooterRepository;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/viajes")
public class ViajeController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ScooterRepository scooterRepository;

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
    public ResponseEntity<Viaje> createViaje(@Valid @RequestBody CreateViajeDto createViajeDto) {
        Optional<Usuario> usuario = usuarioRepository.findById(createViajeDto.getUsuarioId());
        Optional<Scooter> scooter = scooterRepository.findById(createViajeDto.getScooterId());

        if (usuario.isPresent() && scooter.isPresent()) {
            Viaje viaje = new Viaje();
            viaje.setUsuarioId(usuario.get());
            viaje.setScooterId(scooter.get());

            if (createViajeDto.getUbicacionInicioId() != null) {
                scooterRepository.findById(createViajeDto.getUbicacionInicioId()).ifPresent(viaje::setUbicacionInicio);
            }
            if (createViajeDto.getUbicacionFinId() != null) {
                scooterRepository.findById(createViajeDto.getUbicacionFinId()).ifPresent(viaje::setUbicacionFin);
            }

            Viaje nuevoViaje = viajeService.save(viaje);
            return ResponseEntity.ok(nuevoViaje);
        } else {
            return ResponseEntity.badRequest().build();
        }
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


    @PatchMapping("/{id}/start")
    public ResponseEntity<Viaje> startViaje(@PathVariable Long id) {
        try {
            Viaje viaje = viajeService.findById(id).orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + id));
            viaje.setHoraInicio(LocalDateTime.now());
            Viaje viajeActualizado = viajeService.save(viaje);
            return ResponseEntity.ok(viajeActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/end")
    public ResponseEntity<Viaje> endViaje(@PathVariable Long id) {
        try {
            Viaje viaje = viajeService.findById(id).orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + id));
            viaje.setHoraFin(LocalDateTime.now());
            viaje.setCosto(viajeService.calcularCosto(viaje.getHoraInicio(), viaje.getHoraFin()));
            Viaje viajeActualizado = viajeService.save(viaje);
            return ResponseEntity.ok(viajeActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    }

