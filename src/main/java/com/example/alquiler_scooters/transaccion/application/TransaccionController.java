package com.example.alquiler_scooters.transaccion.application;

import com.example.alquiler_scooters.transaccion.domain.Transaccion;
import com.example.alquiler_scooters.transaccion.domain.TransaccionService;
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

    @PostMapping("/crear")
    public ResponseEntity<Transaccion> crearTransaccion(@RequestParam Long usuarioId,
                                                        @RequestParam Long viajeId,
                                                        @RequestParam Double monto) {
        Transaccion transaccion = transaccionService.crearTransaccion(usuarioId, viajeId, monto);
        return ResponseEntity.ok(transaccion);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Transaccion>> obtenerTransaccionesPorUsuario(@PathVariable Long usuarioId) {
        List<Transaccion> transacciones = transaccionService.obtenerTransaccionesPorUsuario(usuarioId);
        return ResponseEntity.ok(transacciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Transaccion>> obtenerTransaccionPorId(@PathVariable Long id) {
        Optional<Transaccion> transaccion = transaccionService.obtenerTransaccionPorId(id);
        return ResponseEntity.ok(transaccion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTransaccion(@PathVariable Long id) {
        transaccionService.eliminarTransaccion(id);
        return ResponseEntity.noContent().build();
    }
}