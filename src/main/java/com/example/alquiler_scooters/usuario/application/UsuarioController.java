package com.example.alquiler_scooters.usuario.application;

import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.domain.UsuarioService;
import com.example.alquiler_scooters.usuario.dto.UsuarioDetallesDto;
import com.example.alquiler_scooters.usuario.exceptions.UsuarioException;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import com.example.alquiler_scooters.viaje.domain.ViajeService;
import com.example.alquiler_scooters.viaje.dto.ViajeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ViajeService viajeService;

    // ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsuarioDetallesDto>> getAllUsuarios() {
        List<UsuarioDetallesDto> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        Optional<UsuarioDetallesDto> usuario = usuarioService.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(400).body("El usuario con ese Id no existe");
        }
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.save(usuario);
        UsuarioDetallesDto usuarioDetallesDto = new UsuarioDetallesDto();
        usuarioDetallesDto.setId(savedUsuario.getId());
        usuarioDetallesDto.setNombre(savedUsuario.getNombre());
        usuarioDetallesDto.setEmail(savedUsuario.getEmail());
        usuarioDetallesDto.setTelefono(savedUsuario.getTelefono());
        usuarioDetallesDto.setFechaRegistro(savedUsuario.getFechaRegistro());
        return ResponseEntity.status(201).body(usuarioDetallesDto);
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.ok("Usuario con el id " + id + " ha sido eliminado correctamente.");
        } catch (UsuarioException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetalles) {
        Usuario updatedUsuario = usuarioService.updateUsuario(id, usuarioDetalles);
        UsuarioDetallesDto usuarioDetallesDto = new UsuarioDetallesDto();
        usuarioDetallesDto.setId(updatedUsuario.getId());
        usuarioDetallesDto.setNombre(updatedUsuario.getNombre());
        usuarioDetallesDto.setEmail(updatedUsuario.getEmail());
        usuarioDetallesDto.setTelefono(updatedUsuario.getTelefono());
        usuarioDetallesDto.setFechaRegistro(updatedUsuario.getFechaRegistro());
        return ResponseEntity.ok(usuarioDetallesDto);
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}/viajes")
    public ResponseEntity<?> getViajesByUsuarioId(@PathVariable Long id) {
        List<ViajeDTO> viajes = viajeService.findByUsuarioId(id);
        if (viajes.isEmpty()) {
            return ResponseEntity.status(404).body("El usuario no tiene viajes");
        }
        return ResponseEntity.ok(viajes);
    }

    // USER
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getUsuarioByEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.findByEmail(email);
        return ResponseEntity.ok(usuario);
    }
}
