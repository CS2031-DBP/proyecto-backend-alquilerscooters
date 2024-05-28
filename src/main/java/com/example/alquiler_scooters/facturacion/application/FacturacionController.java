package com.example.alquiler_scooters.facturacion.application;

import com.example.alquiler_scooters.facturacion.domain.Facturacion;
import com.example.alquiler_scooters.facturacion.domain.FacturacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/facturaciones")
public class FacturacionController {

    @Autowired
    private FacturacionService facturacionService;

    @GetMapping
    public ResponseEntity<List<Facturacion>> getAllFacturaciones() {
        List<Facturacion> facturaciones = facturacionService.findAll();
        return ResponseEntity.ok(facturaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facturacion> getFacturacionById(@PathVariable UUID id) {
        Optional<Facturacion> facturacion = facturacionService.findById(id);
        return facturacion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Facturacion> createFacturacion(@RequestBody Facturacion facturacion) {
        Facturacion savedFacturacion = facturacionService.save(facturacion);
        return ResponseEntity.ok(savedFacturacion);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Facturacion> updateFacturacion(@PathVariable UUID id, @RequestBody Facturacion facturacionDetalles) {
        Facturacion updatedFacturacion = facturacionService.updateFacturacion(id, facturacionDetalles);
        return ResponseEntity.ok(updatedFacturacion);
    }

    // Endpoint para eliminar una transacción por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaccion(@PathVariable UUID id) {
        facturacionService.deleteById(id);
        String message = "Transacción con ID " + id + " ha sido eliminada.";
        return ResponseEntity.ok(message);
    }
}