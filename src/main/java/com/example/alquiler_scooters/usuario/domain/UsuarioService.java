package com.example.alquiler_scooters.usuario.domain;

import com.example.alquiler_scooters.usuario.dto.UserDetailsDto;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    ModelMapper mapper;

    private UserDetailsDto convertToDto(Usuario usuario) {
        return mapper.map(usuario, UserDetailsDto.class);
    }

    public List<UserDetailsDto> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDetailsDto> findById(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertToDto);
    }

    public String save(Usuario usuario) {
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return "Se ha completado su registro " + savedUsuario.getNombre() + ", ¡bienvenido!";
    }

    public String deleteById(Long id) {
        usuarioRepository.deleteById(id);
        return "Usuario con el id " + id + " ha sido eliminado correctamente.";
    }

    public String updateUsuario(Long id, Usuario usuarioDetalles) {
        usuarioRepository.findById(id).map(usuario -> {
            if (usuarioDetalles.getNombre() != null) {
                usuario.setNombre(usuarioDetalles.getNombre());
            }
            if (usuarioDetalles.getEmail() != null) {
                usuario.setEmail(usuarioDetalles.getEmail());
            }
            if (usuarioDetalles.getTelefono() != null) {
                usuario.setTelefono(usuarioDetalles.getTelefono());
            }
            if (usuarioDetalles.getContrasena() != null) {
                usuario.setContrasena(usuarioDetalles.getContrasena());
            }
            usuarioRepository.save(usuario);
            return usuario;
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return "Usuario con id: " + id + " ha sido actualizado.";
    }
}
