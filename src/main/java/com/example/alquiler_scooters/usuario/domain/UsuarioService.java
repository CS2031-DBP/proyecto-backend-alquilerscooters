package com.example.alquiler_scooters.usuario.domain;

import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario updateUsuario(Long id, Usuario usuarioDetalles) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioDetalles.getNombre());
            usuario.setEmail(usuarioDetalles.getEmail());
            usuario.setTelefono(usuarioDetalles.getTelefono());
            usuario.setContrasena(usuarioDetalles.getContrasena());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }
}
