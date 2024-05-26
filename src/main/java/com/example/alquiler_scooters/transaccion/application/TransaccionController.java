package com.example.alquiler_scooters.transaccion.application;

import com.example.alquiler_scooters.transaccion.domain.Transaccion;
import com.example.alquiler_scooters.transaccion.domain.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/transacciones")
public class TransaccionController {
    @Autowired
    private TransaccionService transaccionService;

    @GetMapping
    public List<Transaccion> getAllTransacciones() {
        return transaccionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> getTransaccionById(@PathVariable Long id) {
        Optional<Transaccion> transaccion = transaccionService.findById(id);
        if (transaccion.isPresent()) {
            return ResponseEntity.ok(transaccion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Transaccion createTransaccion(@RequestBody Transaccion transaccion) {
        return transaccionService.save(transaccion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaccion> updateTransaccion(@PathVariable Long id, @RequestBody Transaccion transaccionDetails) {
        Optional<Transaccion> transaccion = transaccionService.findById(id);
        if (transaccion.isPresent()) {
            Transaccion updatedTransaccion = transaccion.get();
            updatedTransaccion.setUsuarioId(transaccionDetails.getUsuarioId());
            updatedTransaccion.setViajeId(transaccionDetails.getViajeId());
            updatedTransaccion.setMonto(transaccionDetails.getMonto());
            updatedTransaccion.setFecha(transaccionDetails.getFecha());
            transaccionService.save(updatedTransaccion);
            return ResponseEntity.ok(updatedTransaccion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Transaccion> partialUpdateTransaccion(@PathVariable Long id, @RequestBody Transaccion transaccionDetails) {
        Optional<Transaccion> optionalTransaccion = transaccionService.findById(id);
        if (optionalTransaccion.isPresent()) {
            Transaccion transaccion = optionalTransaccion.get();
            if (transaccionDetails.getUsuarioId() != null) {
                transaccion.setUsuarioId(transaccionDetails.getUsuarioId());
            }
            if (transaccionDetails.getViajeId() != null) {
                transaccion.setViajeId(transaccionDetails.getViajeId());
            }
            if (transaccionDetails.getMonto() != null) {
                transaccion.setMonto(transaccionDetails.getMonto());
            }
            if (transaccionDetails.getFecha() != null) {
                transaccion.setFecha(transaccionDetails.getFecha());
            }
            transaccionService.save(transaccion);
            return ResponseEntity.ok(transaccion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaccion(@PathVariable Long id) {
        if (transaccionService.findById(id).isPresent()) {
            transaccionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}