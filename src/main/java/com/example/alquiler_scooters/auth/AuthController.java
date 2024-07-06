package com.example.alquiler_scooters.auth;

import com.example.alquiler_scooters.auth.dto.AuthJwtResponse;
import com.example.alquiler_scooters.auth.dto.AuthLoginRequest;
import com.example.alquiler_scooters.auth.dto.AuthRegisterRequest;
import com.example.alquiler_scooters.auth.google.GoogleTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

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
}