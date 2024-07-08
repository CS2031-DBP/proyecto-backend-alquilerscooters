package com.example.alquiler_scooters.auth;

import com.example.alquiler_scooters.auth.dto.AuthJwtResponse;
import com.example.alquiler_scooters.auth.dto.AuthLoginRequest;
import com.example.alquiler_scooters.auth.dto.AuthRegisterRequest;
import com.example.alquiler_scooters.auth.google.GoogleTokenRequest;
import com.example.alquiler_scooters.scooter.domain.ScooterService;
import com.example.alquiler_scooters.scooter.dto.ScooterDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    ScooterService scooterService;

    @PostMapping("/register")
    public ResponseEntity<AuthJwtResponse> register(@RequestBody AuthRegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthJwtResponse> login(@RequestBody AuthLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/google/login")
    public ResponseEntity<AuthJwtResponse> googleLogin(@RequestBody GoogleTokenRequest request) {
        return ResponseEntity.ok(authService.loginWithGoogle(request.getToken()));
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello LOCAL UNU?!";
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ScooterDetailsDto>> getScootersByEstadoDisponible() {
        List<ScooterDetailsDto> scooters = scooterService.findScootersByEstadoDisponible();
        return ResponseEntity.ok(scooters);
    }
}