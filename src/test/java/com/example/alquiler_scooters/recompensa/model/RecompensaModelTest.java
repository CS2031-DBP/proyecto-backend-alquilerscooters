package com.example.alquiler_scooters.recompensa.model;

import com.example.alquiler_scooters.recompensa.domain.Recompensa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecompensaModelTest {
    @Test
    public void testRecompensaEntity() {
        assertNotNull(Recompensa.class.getAnnotation(Entity.class), "Recompensa class should be annotated with @Entity");
    }

    @Test
    public void testIdField() throws NoSuchFieldException {
        assertNotNull(Recompensa.class.getDeclaredField("id"), "id field should be present");
        assertNotNull(Recompensa.class.getDeclaredField("id").getAnnotation(Id.class), "id field should be annotated with @Id");
        assertNotNull(Recompensa.class.getDeclaredField("id").getAnnotation(GeneratedValue.class), "id field should be annotated with @GeneratedValue");
        assertEquals(GenerationType.IDENTITY, Recompensa.class.getDeclaredField("id").getAnnotation(GeneratedValue.class).strategy(), "id field should be generated as IDENTITY");
    }

    @Test
    public void testUsuarioRelation() throws NoSuchFieldException {
        assertNotNull(Recompensa.class.getDeclaredField("usuario"), "usuario field should be present");
        assertNotNull(Recompensa.class.getDeclaredField("usuario").getAnnotation(ManyToOne.class), "usuario field should be annotated with @ManyToOne");
        assertNotNull(Recompensa.class.getDeclaredField("usuario").getAnnotation(JoinColumn.class), "usuario field should be annotated with @JoinColumn");
        assertTrue(Recompensa.class.getDeclaredField("usuario").getAnnotation(JoinColumn.class).nullable(), "usuario field should be nullable");
    }

    @Test
    public void testViajeRelation() throws NoSuchFieldException {
        assertNotNull(Recompensa.class.getDeclaredField("viaje"), "viaje field should be present");
        assertNotNull(Recompensa.class.getDeclaredField("viaje").getAnnotation(OneToOne.class), "viaje field should be annotated with @OneToOne");
        assertNotNull(Recompensa.class.getDeclaredField("viaje").getAnnotation(JoinColumn.class), "viaje field should be annotated with @JoinColumn");
        assertTrue(Recompensa.class.getDeclaredField("viaje").getAnnotation(JoinColumn.class).nullable(), "viaje field should be nullable");
    }

    @Test
    public void testEstacionamientoRelation() throws NoSuchFieldException {
        assertNotNull(Recompensa.class.getDeclaredField("estacionamiento"), "estacionamiento field should be present");
        assertNotNull(Recompensa.class.getDeclaredField("estacionamiento").getAnnotation(ManyToOne.class), "estacionamiento field should be annotated with @ManyToOne");
        assertNotNull(Recompensa.class.getDeclaredField("estacionamiento").getAnnotation(JoinColumn.class), "estacionamiento field should be annotated with @JoinColumn");
        assertTrue(Recompensa.class.getDeclaredField("estacionamiento").getAnnotation(JoinColumn.class).nullable(), "estacionamiento field should be nullable");
    }

    @Test
    public void testNombreField() throws NoSuchFieldException {
        assertNotNull(Recompensa.class.getDeclaredField("nombre"), "nombre field should be present");
    }

    @Test
    public void testDescripcionField() throws NoSuchFieldException {
        assertNotNull(Recompensa.class.getDeclaredField("descripcion"), "descripcion field should be present");
    }

    @Test
    public void testFechaField() throws NoSuchFieldException {
        assertNotNull(Recompensa.class.getDeclaredField("fecha"), "fecha field should be present");
    }

    @Test
    public void testFechaNotNull() throws NoSuchFieldException {
        assertNotNull(Recompensa.class.getDeclaredField("fecha").getAnnotation(NotNull.class), "fecha field should be annotated with @NotNull");
    }
}
