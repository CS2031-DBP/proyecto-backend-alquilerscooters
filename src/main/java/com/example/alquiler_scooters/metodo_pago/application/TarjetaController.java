package com.example.alquiler_scooters.metodo_pago.application;

import com.example.alquiler_scooters.metodo_pago.domain.Tarjeta;
import com.example.alquiler_scooters.metodo_pago.domain.TarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tarjetas")
public class TarjetaController {

    @Autowired
    private TarjetaService tarjetaService;

    @GetMapping
    public ResponseEntity<List<Tarjeta>> getAllTarjetas() {
        List<Tarjeta> tarjetas = tarjetaService.findAll();
        return ResponseEntity.ok(tarjetas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarjeta> getTarjetaById(@PathVariable UUID id) {
        Optional<Tarjeta> tarjeta = tarjetaService.findById(id);
        return tarjeta.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarjeta> createTarjeta(@RequestBody Tarjeta tarjeta) {
        Tarjeta savedTarjeta = tarjetaService.save(tarjeta);
        return ResponseEntity.ok(savedTarjeta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tarjeta> updateTarjeta(@PathVariable UUID id, @RequestBody Tarjeta tarjetaDetalles) {
        Tarjeta updatedTarjeta = tarjetaService.updateTarjeta(id, tarjetaDetalles);
        return ResponseEntity.ok(updatedTarjeta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarjeta(@PathVariable UUID id) {
        tarjetaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}