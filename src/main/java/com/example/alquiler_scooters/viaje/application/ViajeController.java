package com.example.alquiler_scooters.viaje.application;

import com.example.alquiler_scooters.auth.AuthImpl;
import com.example.alquiler_scooters.auth.AuthService;
import com.example.alquiler_scooters.viaje.domain.ViajeService;
import com.example.alquiler_scooters.viaje.dto.ViajeDTO;
import com.example.alquiler_scooters.viaje.exceptions.NoViajesFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/viajes")
public class ViajeController {
    @Autowired
    private ViajeService viajeService;

    @Autowired
    private AuthImpl authImpl;


    // ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ViajeDTO>> getAllViajes() {
        List<ViajeDTO> viajes = viajeService.findAll();
        return ResponseEntity.ok(viajes);
    }

    // ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ViajeDTO> getViajeById(@PathVariable UUID id) {
        Optional<ViajeDTO> viaje = viajeService.findById(id);
        return viaje.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> getViajesByUsuarioId(@PathVariable Long usuarioId) {
        try {
            List<ViajeDTO> viajes = viajeService.findByUsuarioId(usuarioId);
            return ResponseEntity.ok(viajes);
        } catch (NoViajesFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> createViaje(@RequestBody ViajeDTO viajeDTO) {
        String message = viajeService.save(viajeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<String> finalizarViaje(@PathVariable UUID id) {
        String message = viajeService.finalizarViaje(id);
        return ResponseEntity.ok(message);
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViaje(@PathVariable UUID id) {
        viajeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/details")
    public ResponseEntity<?> getUserViajes(@RequestHeader("Authorization") String authHeader) {
        try {
            String email = authImpl.getCurrentEmail(); // Suponiendo que tienes un método para obtener el correo del usuario autenticado
            List<ViajeDTO> viajes = viajeService.findByUsuarioEmail(email);
            if (viajes.isEmpty()) {
                return ResponseEntity.ok("No trips found");
            }
            return ResponseEntity.ok(viajes);
        } catch (NoViajesFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> createViaje(@RequestHeader("Authorization") String authHeader, @RequestBody ScooterIdRequest scooterIdRequest) {
        try {
            String email = authImpl.getCurrentEmail();
            String message = viajeService.saveViajeWithEmailAndScooterId(email, scooterIdRequest.getScooterId());
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public static class ScooterIdRequest {
        private UUID scooterId;

        public UUID getScooterId() {
            return scooterId;
        }

        public void setScooterId(UUID scooterId) {
            this.scooterId = scooterId;
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PatchMapping("/end/{id}")
    public ResponseEntity<String> endTrip(@PathVariable UUID id) {
        try {
            String message = viajeService.finalizarViaje(id);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}