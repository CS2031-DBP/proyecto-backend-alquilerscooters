package com.example.alquiler_scooters.auth;

import com.example.alquiler_scooters.auth.dto.AuthJwtResponse;
import com.example.alquiler_scooters.auth.dto.AuthLoginRequest;
import com.example.alquiler_scooters.auth.dto.AuthRegisterRequest;
import com.example.alquiler_scooters.auth.google.GoogleService;
import com.example.alquiler_scooters.auth.google.GoogleTokenRequest;
import com.example.alquiler_scooters.auth.google.GoogleTokenResponse;
import com.example.alquiler_scooters.config.JwtService;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import com.example.alquiler_scooters.usuario.infrastructure.UserLoginRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final UserLoginRepository userLoginRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final GoogleService googleService;

    @Autowired
    public AuthService(UsuarioRepository usuarioRepository, UserLoginRepository userLoginRepository, JwtService jwtService, PasswordEncoder passwordEncoder, ModelMapper modelMapper, GoogleService googleService) {
        this.usuarioRepository = usuarioRepository;
        this.userLoginRepository = userLoginRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.googleService = googleService;
    }

    public AuthJwtResponse login(AuthLoginRequest req) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(req.getEmail());

        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("Email is not registered");
        }

        if (!passwordEncoder.matches(req.getPassword(), usuario.get().getContrasena())) {
            throw new IllegalArgumentException("Password is incorrect");
        }

        CustomUserDetails userDetails = new CustomUserDetails(usuario.get());
        String token = jwtService.generateToken(userDetails);

        return new AuthJwtResponse(token);
    }

    public AuthJwtResponse register(AuthRegisterRequest req) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(req.getEmail());
        if (usuario.isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setNombre(req.getName());
        newUsuario.setEmail(req.getEmail());
        newUsuario.setContrasena(passwordEncoder.encode(req.getPassword()));
        newUsuario.setTelefono(req.getPhone());
        newUsuario.setFechaRegistro(LocalDate.now());
        newUsuario.setRole(req.getRole());

        usuarioRepository.save(newUsuario);

        CustomUserDetails userDetails = new CustomUserDetails(newUsuario);
        String token = jwtService.generateToken(userDetails);

        // Log the token
        System.out.println("Generated JWT token: " + token);

        return new AuthJwtResponse(token);
    }

    public AuthJwtResponse loginWithGoogle(String token) {
        GoogleTokenRequest googleTokenRequest = new GoogleTokenRequest(token);
        GoogleTokenResponse googleTokenResponse = googleService.validate(googleTokenRequest);

        if (!googleTokenResponse.isValidate()) {
            throw new IllegalArgumentException("Invalid Google token");
        }

        return new AuthJwtResponse(googleTokenResponse.getToken());
    }
}
