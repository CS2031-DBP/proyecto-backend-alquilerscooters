package com.example.alquiler_scooters.transaccion.application;

import com.example.alquiler_scooters.transaccion.domain.Transaccion;
import com.example.alquiler_scooters.transaccion.domain.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/transacciones")
public class TransaccionController {
    @Autowired
    private TransaccionService transaccionService;

    // Endpoint para obtener todas las transacciones
    @GetMapping
    public ResponseEntity<List<Transaccion>> getAllTransacciones() {
        List<Transaccion> transacciones = transaccionService.findAll();
        return ResponseEntity.ok(transacciones);
    }

    // Endpoint para obtener una transacción por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> getTransaccionById(@PathVariable Long id) {
        Optional<Transaccion> transaccion = transaccionService.findById(id);
        return transaccion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para crear una nueva transacción
    @PostMapping
    public ResponseEntity<Transaccion> createTransaccion(@RequestBody Transaccion transaccion) {
        Transaccion nuevaTransaccion = transaccionService.save(transaccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTransaccion);
    }

    // Endpoint para actualizar una transacción existente
    @PutMapping("/{id}")
    public ResponseEntity<Transaccion> updateTransaccion(@PathVariable Long id, @RequestBody Transaccion transaccionDetails) {
        Optional<Transaccion> updatedTransaccion = transaccionService.update(id, transaccionDetails);
        return updatedTransaccion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Transaccion> partialUpdateTransaccion(@PathVariable Long id, @RequestBody Transaccion transaccionDetails) {
        Optional<Transaccion> updatedTransaccion = transaccionService.partialUpdate(id, transaccionDetails);
        return updatedTransaccion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para eliminar una transacción por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaccion(@PathVariable Long id) {
        transaccionService.deleteById(id);
        String message = "Transacción con ID " + id + " ha sido eliminada.";
        return ResponseEntity.ok(message);
    }
}