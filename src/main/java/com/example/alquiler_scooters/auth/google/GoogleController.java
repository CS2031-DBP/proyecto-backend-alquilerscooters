package com.example.alquiler_scooters.auth.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class GoogleController {

    @Autowired
    GoogleService service;

    @PostMapping("/google/validate")
    public ResponseEntity<GoogleTokenResponse> token(@RequestBody GoogleTokenRequest request) {
        return ResponseEntity.ok(service.validate(request));
    }
}