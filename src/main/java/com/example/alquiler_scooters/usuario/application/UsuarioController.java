package com.example.alquiler_scooters.usuario.application;

import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.domain.UsuarioService;
import com.example.alquiler_scooters.usuario.dto.UserDetailsDto;
import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<UserDetailsDto>> getAllUsuarios() {
        List<UserDetailsDto> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUsuarioById(@PathVariable Long id) {
        Optional<UserDetailsDto> usuario = usuarioService.findById(id);
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

}
