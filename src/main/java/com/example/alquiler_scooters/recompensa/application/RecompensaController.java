package com.example.alquiler_scooters.recompensa.application;


import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import com.example.alquiler_scooters.recompensa.domain.RecompensaService;
import com.example.alquiler_scooters.recompensa.dto.RecompensaDTO;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
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
        return recompensaService.getRecompensaById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<RecompensaDTO> getRecompensas(@PathVariable Long usuarioId) {
        return recompensaService.getRecompensasByUsuarioId(usuarioId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<RecompensaDTO> createRecompensa(@RequestBody RecompensaDTO recompensaDTO) {
        Recompensa recompensa = convertToEntity(recompensaDTO);
        Recompensa createdRecompensa = recompensaService.createRecompensa(recompensa);
        return ResponseEntity.ok(convertToDTO(createdRecompensa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecompensa(@PathVariable Long id) {
        recompensaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private RecompensaDTO convertToDTO(Recompensa recompensa) {
        RecompensaDTO recompensaDTO = new RecompensaDTO();
        recompensaDTO.setId(recompensa.getId());
        recompensaDTO.setUsuarioId(recompensa.getUsuario().getId());
        recompensaDTO.setNombre(recompensa.getNombre());
        recompensaDTO.setDescripcion(recompensa.getDescripcion());
        recompensaDTO.setFecha(recompensa.getFecha());
        return recompensaDTO;
    }

    private Recompensa convertToEntity(RecompensaDTO recompensaDTO) {
        Recompensa recompensa = new Recompensa();
        recompensa.setId(recompensaDTO.getId());
        Usuario usuario = new Usuario();
        usuario.setId(recompensaDTO.getUsuarioId());
        recompensa.setUsuario(usuario);
        recompensa.setNombre(recompensaDTO.getNombre());
        recompensa.setDescripcion(recompensaDTO.getDescripcion());
        recompensa.setFecha(recompensaDTO.getFecha());
        return recompensa;
    }
}
