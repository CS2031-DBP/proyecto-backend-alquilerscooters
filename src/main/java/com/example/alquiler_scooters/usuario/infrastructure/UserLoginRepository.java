package com.example.alquiler_scooters.usuario.infrastructure;

import com.example.alquiler_scooters.usuario.domain.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    Optional<UserLogin> findByProviderAndProviderId(String provider, String providerId);
}
