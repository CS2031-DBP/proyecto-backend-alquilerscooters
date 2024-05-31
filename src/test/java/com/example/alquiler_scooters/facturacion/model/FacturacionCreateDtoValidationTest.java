package com.example.alquiler_scooters.facturacion.model;

import com.example.alquiler_scooters.facturacion.domain.FacturacionCreateDTO;
import com.example.alquiler_scooters.metodo_pago.domain.TarjetaDTO;
import com.example.alquiler_scooters.usuario.domain.UsuarioDto;
import com.example.alquiler_scooters.viaje.dto.FacturaViajeDTO;
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
public class FacturacionCreateDtoValidationTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
    }

    @Test
    void shouldFailValidationWhenIdIsNull() {
        FacturacionCreateDTO facturacionCreateDTO = new FacturacionCreateDTO();
        facturacionCreateDTO.setUsuario(new UsuarioDto());
        facturacionCreateDTO.setViaje(new FacturaViajeDTO());
        facturacionCreateDTO.setTarjeta(new TarjetaDTO());

        Set<ConstraintViolation<FacturacionCreateDTO>> violations = validator.validate(facturacionCreateDTO);

        // Assuming 'id' field is required, change the expected size accordingly
        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenUsuarioIsNull() {
        FacturacionCreateDTO facturacionCreateDTO = new FacturacionCreateDTO();
        facturacionCreateDTO.setId(UUID.randomUUID());
        facturacionCreateDTO.setViaje(new FacturaViajeDTO());
        facturacionCreateDTO.setTarjeta(new TarjetaDTO());

        Set<ConstraintViolation<FacturacionCreateDTO>> violations = validator.validate(facturacionCreateDTO);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenViajeIsNull() {
        FacturacionCreateDTO facturacionCreateDTO = new FacturacionCreateDTO();
        facturacionCreateDTO.setId(UUID.randomUUID());
        facturacionCreateDTO.setUsuario(new UsuarioDto());
        facturacionCreateDTO.setTarjeta(new TarjetaDTO());

        Set<ConstraintViolation<FacturacionCreateDTO>> violations = validator.validate(facturacionCreateDTO);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenTarjetaIsNull() {
        FacturacionCreateDTO facturacionCreateDTO = new FacturacionCreateDTO();
        facturacionCreateDTO.setId(UUID.randomUUID());
        facturacionCreateDTO.setUsuario(new UsuarioDto());
        facturacionCreateDTO.setViaje(new FacturaViajeDTO());

        Set<ConstraintViolation<FacturacionCreateDTO>> violations = validator.validate(facturacionCreateDTO);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldPassValidationWithValidData() {
        FacturacionCreateDTO facturacionCreateDTO = new FacturacionCreateDTO();
        facturacionCreateDTO.setId(UUID.randomUUID());
        facturacionCreateDTO.setUsuario(new UsuarioDto());
        facturacionCreateDTO.setViaje(new FacturaViajeDTO());
        facturacionCreateDTO.setTarjeta(new TarjetaDTO());

        Set<ConstraintViolation<FacturacionCreateDTO>> violations = validator.validate(facturacionCreateDTO);

        assertEquals(0, violations.size());
    }
}
