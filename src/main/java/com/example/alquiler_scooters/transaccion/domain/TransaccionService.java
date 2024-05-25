package com.example.alquiler_scooters.transaccion.domain;

import com.example.alquiler_scooters.transaccion.infrastructure.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransaccionService {
    @Autowired
    private TransaccionRepository transaccionRepository;

    public List<Transaccion> getAllTransacciones() {
        return transaccionRepository.findAll();
    }

    public Optional<Transaccion> getTransaccionById(Long id) {
        return transaccionRepository.findById(id);
    }

    public List<Transaccion> getTransaccionesByUsuarioId(Long usuarioId) {
        return transaccionRepository.findByUsuarioId(usuarioId);
    }

    public Transaccion createTransaccion(Long usuarioId, Long viajeId, Double monto) {
        Transaccion transaccion = new Transaccion();
        transaccion.setUsuarioId(usuarioId);
        transaccion.setViajeId(viajeId);
        transaccion.setMonto(monto);
        transaccion.setFecha(LocalDateTime.now());
        return transaccionRepository.save(transaccion);
    }

    public void deleteTransaccion(Long id) {
        transaccionRepository.deleteById(id);
    }
}
