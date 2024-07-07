package com.example.alquiler_scooters.usuario.domain;

import com.example.alquiler_scooters.usuario.dto.UsuarioDetallesDto;
import com.example.alquiler_scooters.usuario.exceptions.UsuarioException;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper mapper;

    private UsuarioDetallesDto convertToDto(Usuario usuario) {
        return mapper.map(usuario, UsuarioDetallesDto.class);
    }

    public UsuarioDetallesDto getUserDetails(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            return convertToDto(usuarioOpt.get());
        }
        throw new UsuarioException("User not found", 404);
    }

    public List<UsuarioDetallesDto> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDetallesDto> findById(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertToDto);
    }

    public Usuario save(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new UsuarioException("Los datos ingresados no son correctos.", 400);
        }
    }

    public void deleteById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
        } else {
            throw new UsuarioException("El usuario con ese Id no existe", 400);
        }
    }

    public Usuario updateUsuario(Long id, Usuario usuarioDetalles) {
        return usuarioRepository.findById(id).map(usuario -> {
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
            try {
                return usuarioRepository.save(usuario);
            } catch (Exception e) {
                throw new UsuarioException("No puedes actualizar los datos, ya que son incorrectos", 400);
            }
        }).orElseThrow(() -> new UsuarioException("Usuario no encontrado con id: " + id, 400));
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = findByEmail(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()));
        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getContrasena(), authorities);
    }

    public Usuario updateUsuarioByEmail(String email, Usuario usuarioDetalles) {
        return usuarioRepository.findByEmail(email).map(usuario -> {
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
            try {
                return usuarioRepository.save(usuario);
            } catch (Exception e) {
                throw new UsuarioException("No puedes actualizar los datos, ya que son incorrectos", 400);
            }
        }).orElseThrow(() -> new UsuarioException("Usuario no encontrado con email: " + email, 400));
    }


}
