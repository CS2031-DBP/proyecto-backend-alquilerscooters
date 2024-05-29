package com.example.alquiler_scooters.recompensa.application;


import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import com.example.alquiler_scooters.recompensa.domain.RecompensaService;
import com.example.alquiler_scooters.recompensa.dto.RecompensaDTO;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recompensa")
public class RecompensaController {
    @Autowired
    private RecompensaService recompensaService;

    @GetMapping
    public List<RecompensaDTO> getAllRecompensas() {
        return recompensaService.getAllRecompensas()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecompensaDTO> getRecompensaById(@PathVariable Long id) {
        try {
            Recompensa recompensa = recompensaService.getRecompensaById(id);
            return ResponseEntity.ok(convertToDTO(recompensa));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<RecompensaDTO> getRecompensasByUsuarioId(@PathVariable Long usuarioId) {
        return recompensaService.getRecompensasByUsuarioId(usuarioId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

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
    public ResponseEntity<Void> deleteRecompensa(@PathVariable Long id) {
        try {
            recompensaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private RecompensaDTO convertToDTO(Recompensa recompensa) {
        RecompensaDTO recompensaDTO = new RecompensaDTO();
        recompensaDTO.setId(recompensa.getId());
        recompensaDTO.setUsuarioId(recompensa.getUsuario().getId());
        recompensaDTO.setViajeId(recompensa.getViaje().getId());
        recompensaDTO.setNombre(recompensa.getNombre());
        recompensaDTO.setDescripcion(recompensa.getDescripcion());
        recompensaDTO.setFecha(recompensa.getFecha());
        return recompensaDTO;
    }
}
