package com.example.alquiler_scooters.usuario.application;

import com.example.alquiler_scooters.auth.AuthImpl;
import com.example.alquiler_scooters.auth.AuthService;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.domain.UsuarioService;
import com.example.alquiler_scooters.usuario.dto.UsuarioDetallesDto;
import com.example.alquiler_scooters.usuario.exceptions.UsuarioException;
import com.example.alquiler_scooters.viaje.domain.ViajeService;
import com.example.alquiler_scooters.viaje.dto.ViajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private AuthImpl authImpl;

    @Autowired
    private AuthService authService;

    // ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsuarioDetallesDto>> getAllUsuarios() {
        List<UsuarioDetallesDto> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello")
    public String hello() {
        return "Hello local!";
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roleuser")
    public String roleUser() {
        return "Hello role user!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roleadmin")
    public String roleAdmin() {
        return "Hello role admin!";
    }

    @GetMapping("/details")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UsuarioDetallesDto> getUserDetails(@RequestHeader("Authorization") String authHeader) {
        String email = authImpl.getCurrentEmail();
        UsuarioDetallesDto userDetails = usuarioService.getUserDetails(email);
        return ResponseEntity.ok(userDetails);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/me")
    public ResponseEntity<?> updateUserDetails(@RequestHeader("Authorization") String token, @RequestBody Usuario usuarioDetalles) {
        // Extract the JWT token from the Authorization header
        String jwtToken = token.replace("Bearer ", "");
        // Get the email from the JWT
        String email = authService.getEmailFromJwt(jwtToken);
        // Update user details
        Usuario updatedUsuario = usuarioService.updateUsuarioByEmail(email, usuarioDetalles);
        return ResponseEntity.ok(updatedUsuario);
    }
}
