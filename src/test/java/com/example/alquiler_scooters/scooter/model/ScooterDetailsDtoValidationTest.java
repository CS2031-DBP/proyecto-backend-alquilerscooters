package com.example.alquiler_scooters.scooter.model;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.scooter.dto.ScooterDetailsDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ScooterDetailsDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
    }

    @Test
    void shouldFailValidationWhenIdIsNull() {
        ScooterDetailsDto scooterDetailsDto = new ScooterDetailsDto();
        scooterDetailsDto.setEstado(Scooter.EstadoScooter.DISPONIBLE);
        scooterDetailsDto.setNivelBateria(50);
        scooterDetailsDto.setUbicacionActual("Calle Falsa 123");

        Set<ConstraintViolation<ScooterDetailsDto>> violations = validator.validate(scooterDetailsDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenEstadoIsNull() {
        ScooterDetailsDto scooterDetailsDto = new ScooterDetailsDto();
        scooterDetailsDto.setId(UUID.randomUUID());
        scooterDetailsDto.setNivelBateria(50);
        scooterDetailsDto.setUbicacionActual("Calle Falsa 123");

        Set<ConstraintViolation<ScooterDetailsDto>> violations = validator.validate(scooterDetailsDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenNivelBateriaIsInvalid() {
        ScooterDetailsDto scooterDetailsDto = new ScooterDetailsDto();
        scooterDetailsDto.setId(UUID.randomUUID());
        scooterDetailsDto.setEstado(Scooter.EstadoScooter.DISPONIBLE);
        scooterDetailsDto.setNivelBateria(150);  // Invalid battery level
        scooterDetailsDto.setUbicacionActual("Calle Falsa 123");

        Set<ConstraintViolation<ScooterDetailsDto>> violations = validator.validate(scooterDetailsDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenUbicacionActualIsEmpty() {
        ScooterDetailsDto scooterDetailsDto = new ScooterDetailsDto();
        scooterDetailsDto.setId(UUID.randomUUID());
        scooterDetailsDto.setEstado(Scooter.EstadoScooter.DISPONIBLE);
        scooterDetailsDto.setNivelBateria(50);
        scooterDetailsDto.setUbicacionActual("");

        Set<ConstraintViolation<ScooterDetailsDto>> violations = validator.validate(scooterDetailsDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldPassValidationWithValidData() {
        ScooterDetailsDto scooterDetailsDto = new ScooterDetailsDto();
        scooterDetailsDto.setId(UUID.randomUUID());
        scooterDetailsDto.setEstado(Scooter.EstadoScooter.DISPONIBLE);
        scooterDetailsDto.setNivelBateria(50);
        scooterDetailsDto.setUbicacionActual("Calle Falsa 123");

        Set<ConstraintViolation<ScooterDetailsDto>> violations = validator.validate(scooterDetailsDto);

        assertEquals(0, violations.size());
    }
}