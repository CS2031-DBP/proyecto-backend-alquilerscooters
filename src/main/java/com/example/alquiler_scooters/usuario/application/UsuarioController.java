package com.example.alquiler_scooters.usuario.application;

import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.domain.UsuarioService;
import com.example.alquiler_scooters.usuario.dto.UsuarioDetallesDto;
import com.example.alquiler_scooters.viaje.domain.ViajeService;
import com.example.alquiler_scooters.viaje.dto.ViajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ViajeService viajeService;

    @GetMapping
    public ResponseEntity<List<UsuarioDetallesDto>> getAllUsuarios() {
        List<UsuarioDetallesDto> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDetallesDto> getUsuarioById(@PathVariable Long id) {
        Optional<UsuarioDetallesDto> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUsuario(@RequestBody Usuario usuario) {
        String result = usuarioService.save(usuario);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id) {
        String result = usuarioService.deleteById(id);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetalles) {
        String result = usuarioService.updateUsuario(id, usuarioDetalles);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/viajes")
    public ResponseEntity<List<ViajeDTO>> getViajesByUsuarioId(@PathVariable Long id) {
        List<ViajeDTO> viajes = viajeService.findByUsuarioId(id);
        return ResponseEntity.ok(viajes);
    }

}
