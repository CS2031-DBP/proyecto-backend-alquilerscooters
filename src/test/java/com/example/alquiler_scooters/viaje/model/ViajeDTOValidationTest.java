package com.example.alquiler_scooters.viaje.model;

import com.example.alquiler_scooters.viaje.dto.ScooterSimpleDTO;
import com.example.alquiler_scooters.viaje.dto.UsuarioSimpleDTO;
import com.example.alquiler_scooters.viaje.dto.ViajeDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ViajeDTOValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
    }

    @Test
    void shouldFailValidationWhenIdIsNull() {
        ViajeDTO viajeDTO = new ViajeDTO();
        viajeDTO.setUsuario(new UsuarioSimpleDTO());
        viajeDTO.setScooter(new ScooterSimpleDTO());
        viajeDTO.setHoraInicio(LocalDateTime.now());
        viajeDTO.setPuntoPartida("Punto A");
        viajeDTO.setPuntoFin("Punto B");
        viajeDTO.setCosto(10.0);
        viajeDTO.setEstado("En progreso");

        Set<ConstraintViolation<ViajeDTO>> violations = validator.validate(viajeDTO);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenUsuarioIsNull() {
        ViajeDTO viajeDTO = new ViajeDTO();
        viajeDTO.setId(UUID.randomUUID());
        viajeDTO.setScooter(new ScooterSimpleDTO());
        viajeDTO.setHoraInicio(LocalDateTime.now());
        viajeDTO.setPuntoPartida("Punto A");
        viajeDTO.setPuntoFin("Punto B");
        viajeDTO.setCosto(10.0);
        viajeDTO.setEstado("En progreso");

        Set<ConstraintViolation<ViajeDTO>> violations = validator.validate(viajeDTO);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenScooterIsNull() {
        ViajeDTO viajeDTO = new ViajeDTO();
        viajeDTO.setId(UUID.randomUUID());
        viajeDTO.setUsuario(new UsuarioSimpleDTO());
        viajeDTO.setHoraInicio(LocalDateTime.now());
        viajeDTO.setPuntoPartida("Punto A");
        viajeDTO.setPuntoFin("Punto B");
        viajeDTO.setCosto(10.0);
        viajeDTO.setEstado("En progreso");

        Set<ConstraintViolation<ViajeDTO>> violations = validator.validate(viajeDTO);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenHoraInicioIsNull() {
        ViajeDTO viajeDTO = new ViajeDTO();
        viajeDTO.setId(UUID.randomUUID());
        viajeDTO.setUsuario(new UsuarioSimpleDTO());
        viajeDTO.setScooter(new ScooterSimpleDTO());
        viajeDTO.setPuntoPartida("Punto A");
        viajeDTO.setPuntoFin("Punto B");
        viajeDTO.setCosto(10.0);
        viajeDTO.setEstado("En progreso");

        Set<ConstraintViolation<ViajeDTO>> violations = validator.validate(viajeDTO);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenPuntoPartidaIsEmpty() {
        ViajeDTO viajeDTO = new ViajeDTO();
        viajeDTO.setId(UUID.randomUUID());
        viajeDTO.setUsuario(new UsuarioSimpleDTO());
        viajeDTO.setScooter(new ScooterSimpleDTO());
        viajeDTO.setHoraInicio(LocalDateTime.now());
        viajeDTO.setPuntoPartida("");
        viajeDTO.setPuntoFin("Punto B");
        viajeDTO.setCosto(10.0);
        viajeDTO.setEstado("En progreso");

        Set<ConstraintViolation<ViajeDTO>> violations = validator.validate(viajeDTO);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenPuntoFinIsEmpty() {
        ViajeDTO viajeDTO = new ViajeDTO();
        viajeDTO.setId(UUID.randomUUID());
        viajeDTO.setUsuario(new UsuarioSimpleDTO());
        viajeDTO.setScooter(new ScooterSimpleDTO());
        viajeDTO.setHoraInicio(LocalDateTime.now());
        viajeDTO.setPuntoPartida("Punto A");
        viajeDTO.setPuntoFin("");
        viajeDTO.setCosto(10.0);
        viajeDTO.setEstado("En progreso");

        Set<ConstraintViolation<ViajeDTO>> violations = validator.validate(viajeDTO);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenCostoIsInvalid() {
        ViajeDTO viajeDTO = new ViajeDTO();
        viajeDTO.setId(UUID.randomUUID());
        viajeDTO.setUsuario(new UsuarioSimpleDTO());
        viajeDTO.setScooter(new ScooterSimpleDTO());
        viajeDTO.setHoraInicio(LocalDateTime.now());
        viajeDTO.setPuntoPartida("Punto A");
        viajeDTO.setPuntoFin("Punto B");
        viajeDTO.setCosto(-1.0);  // Invalid cost
        viajeDTO.setEstado("En progreso");

        Set<ConstraintViolation<ViajeDTO>> violations = validator.validate(viajeDTO);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenEstadoIsEmpty() {
        ViajeDTO viajeDTO = new ViajeDTO();
        viajeDTO.setId(UUID.randomUUID());
        viajeDTO.setUsuario(new UsuarioSimpleDTO());
        viajeDTO.setScooter(new ScooterSimpleDTO());
        viajeDTO.setHoraInicio(LocalDateTime.now());
        viajeDTO.setPuntoPartida("Punto A");
        viajeDTO.setPuntoFin("Punto B");
        viajeDTO.setCosto(10.0);
        viajeDTO.setEstado("");

        Set<ConstraintViolation<ViajeDTO>> violations = validator.validate(viajeDTO);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldPassValidationWithValidData() {
        ViajeDTO viajeDTO = new ViajeDTO();
        viajeDTO.setId(UUID.randomUUID());
        viajeDTO.setUsuario(new UsuarioSimpleDTO());
        viajeDTO.setScooter(new ScooterSimpleDTO());
        viajeDTO.setHoraInicio(LocalDateTime.now());
        viajeDTO.setHoraFin(LocalDateTime.now().plusHours(1));
        viajeDTO.setPuntoPartida("Punto A");
        viajeDTO.setPuntoFin("Punto B");
        viajeDTO.setCosto(10.0);
        viajeDTO.setEstado("En progreso");

        Set<ConstraintViolation<ViajeDTO>> violations = validator.validate(viajeDTO);

        assertEquals(0, violations.size());
    }
}
