package com.example.alquiler_scooters.recompensa.application;


import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import com.example.alquiler_scooters.recompensa.domain.RecompensaService;
import com.example.alquiler_scooters.recompensa.dto.RecompensaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recompensa")
public class RecompensaController {
    @Autowired
    private RecompensaService recompensaService;

    // ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<RecompensaDTO> getAllRecompensas() {
        return recompensaService.getAllRecompensas()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RecompensaDTO> getRecompensaById(@PathVariable Long id) {
        try {
            Recompensa recompensa = recompensaService.getRecompensaById(id);
            return ResponseEntity.ok(convertToDTO(recompensa));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/usuario/{usuarioId}")
    public List<RecompensaDTO> getRecompensasByUsuarioId(@PathVariable Long usuarioId) {
        return recompensaService.getRecompensasByUsuarioId(usuarioId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RecompensaDTO> createRecompensa(@RequestBody RecompensaDTO recompensaDTO) {
        try {
            Recompensa recompensa = recompensaService.createRecompensa(recompensaDTO.getViajeId(), recompensaDTO.getNombre(), recompensaDTO.getDescripcion());
            return ResponseEntity.ok(convertToDTO(recompensa));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRecompensa(@PathVariable Long id) {
        try {
            recompensaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }




    private RecompensaDTO convertToDTO(Recompensa recompensa) {
        RecompensaDTO dto = new RecompensaDTO();
        dto.setId(recompensa.getId());
        dto.setNombre(recompensa.getNombre());
        dto.setDescripcion(recompensa.getDescripcion());

        if (recompensa.getViaje() != null) {
            dto.setViajeId(recompensa.getViaje().getId());
        }

        if (recompensa.getUsuario() != null) {
            dto.setUsuarioId(recompensa.getUsuario().getId());
        }
        return dto;
    }
}