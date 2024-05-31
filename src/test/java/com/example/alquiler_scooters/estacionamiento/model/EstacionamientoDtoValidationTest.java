package com.example.alquiler_scooters.estacionamiento.model;

import com.example.alquiler_scooters.estacionamiento.dto.EstacionamientoDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EstacionamientoDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
    }

    @Test
    void shouldFailValidationWhenIdIsNull() {
        EstacionamientoDto estacionamientoDto = new EstacionamientoDto();
        estacionamientoDto.setNombre("Estacionamiento Central");
        estacionamientoDto.setUbicacion("Calle Falsa 123");
        estacionamientoDto.setScooters(Collections.emptyList());

        Set<ConstraintViolation<EstacionamientoDto>> violations = validator.validate(estacionamientoDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenNombreIsEmpty() {
        EstacionamientoDto estacionamientoDto = new EstacionamientoDto();
        estacionamientoDto.setId(1L);
        estacionamientoDto.setNombre("");
        estacionamientoDto.setUbicacion("Calle Falsa 123");
        estacionamientoDto.setScooters(Collections.emptyList());

        Set<ConstraintViolation<EstacionamientoDto>> violations = validator.validate(estacionamientoDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenUbicacionIsEmpty() {
        EstacionamientoDto estacionamientoDto = new EstacionamientoDto();
        estacionamientoDto.setId(1L);
        estacionamientoDto.setNombre("Estacionamiento Central");
        estacionamientoDto.setUbicacion("");
        estacionamientoDto.setScooters(Collections.emptyList());

        Set<ConstraintViolation<EstacionamientoDto>> violations = validator.validate(estacionamientoDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldPassValidationWithValidData() {
        EstacionamientoDto estacionamientoDto = new EstacionamientoDto();
        estacionamientoDto.setId(1L);
        estacionamientoDto.setNombre("Estacionamiento Central");
        estacionamientoDto.setUbicacion("Calle Falsa 123");
        estacionamientoDto.setScooters(Collections.emptyList());

        Set<ConstraintViolation<EstacionamientoDto>> violations = validator.validate(estacionamientoDto);

        assertEquals(0, violations.size());
    }
}
