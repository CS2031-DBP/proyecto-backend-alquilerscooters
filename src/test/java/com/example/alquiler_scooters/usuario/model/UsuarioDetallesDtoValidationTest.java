package com.example.alquiler_scooters.usuario.model;

import com.example.alquiler_scooters.usuario.dto.UsuarioDetallesDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UsuarioDetallesDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
    }

    @Test
    void shouldFailValidationWhenIdIsNull() {
        UsuarioDetallesDto usuarioDetallesDto = new UsuarioDetallesDto();
        usuarioDetallesDto.setNombre("Juan Perez");
        usuarioDetallesDto.setEmail("juan.perez@example.com");
        usuarioDetallesDto.setTelefono("123456789");
        usuarioDetallesDto.setFechaRegistro(LocalDate.now());

        Set<ConstraintViolation<UsuarioDetallesDto>> violations = validator.validate(usuarioDetallesDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenNombreIsEmpty() {
        UsuarioDetallesDto usuarioDetallesDto = new UsuarioDetallesDto();
        usuarioDetallesDto.setId(1L);
        usuarioDetallesDto.setNombre("");
        usuarioDetallesDto.setEmail("juan.perez@example.com");
        usuarioDetallesDto.setTelefono("123456789");
        usuarioDetallesDto.setFechaRegistro(LocalDate.now());

        Set<ConstraintViolation<UsuarioDetallesDto>> violations = validator.validate(usuarioDetallesDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenEmailIsInvalid() {
        UsuarioDetallesDto usuarioDetallesDto = new UsuarioDetallesDto();
        usuarioDetallesDto.setId(1L);
        usuarioDetallesDto.setNombre("Juan Perez");
        usuarioDetallesDto.setEmail("invalid-email");
        usuarioDetallesDto.setTelefono("123456789");
        usuarioDetallesDto.setFechaRegistro(LocalDate.now());

        Set<ConstraintViolation<UsuarioDetallesDto>> violations = validator.validate(usuarioDetallesDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenTelefonoIsEmpty() {
        UsuarioDetallesDto usuarioDetallesDto = new UsuarioDetallesDto();
        usuarioDetallesDto.setId(1L);
        usuarioDetallesDto.setNombre("Juan Perez");
        usuarioDetallesDto.setEmail("juan.perez@example.com");
        usuarioDetallesDto.setTelefono("");
        usuarioDetallesDto.setFechaRegistro(LocalDate.now());

        Set<ConstraintViolation<UsuarioDetallesDto>> violations = validator.validate(usuarioDetallesDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenFechaRegistroIsNull() {
        UsuarioDetallesDto usuarioDetallesDto = new UsuarioDetallesDto();
        usuarioDetallesDto.setId(1L);
        usuarioDetallesDto.setNombre("Juan Perez");
        usuarioDetallesDto.setEmail("juan.perez@example.com");
        usuarioDetallesDto.setTelefono("123456789");

        Set<ConstraintViolation<UsuarioDetallesDto>> violations = validator.validate(usuarioDetallesDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldPassValidationWithValidData() {
        UsuarioDetallesDto usuarioDetallesDto = new UsuarioDetallesDto();
        usuarioDetallesDto.setId(1L);
        usuarioDetallesDto.setNombre("Juan Perez");
        usuarioDetallesDto.setEmail("juan.perez@example.com");
        usuarioDetallesDto.setTelefono("123456789");
        usuarioDetallesDto.setFechaRegistro(LocalDate.now());

        Set<ConstraintViolation<UsuarioDetallesDto>> violations = validator.validate(usuarioDetallesDto);

        assertEquals(0, violations.size());
    }
}
