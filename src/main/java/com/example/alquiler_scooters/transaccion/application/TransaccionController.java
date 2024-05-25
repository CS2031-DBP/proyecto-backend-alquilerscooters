package com.example.alquiler_scooters.transaccion.application;

import com.example.alquiler_scooters.transaccion.domain.Transaccion;
import com.example.alquiler_scooters.transaccion.domain.TransaccionService;
import com.example.alquiler_scooters.transaccion.infrastructure.MetododePagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {
    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private MetododePagoRepository metododePagoRepository;



    @GetMapping
    public List<Transaccion> getAllTransacciones() {
        return transaccionService.getAllTransacciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> getTransaccionById(@PathVariable Long id) {
        return transaccionService.getTransaccionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Transaccion> getTransaccionesByUsuarioId(@PathVariable Long usuarioId) {
        return transaccionService.getTransaccionesByUsuarioId(usuarioId);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaccion(@PathVariable Long id) {
        if (transaccionService.getTransaccionById(id).isPresent()) {
            transaccionService.deleteTransaccion(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}