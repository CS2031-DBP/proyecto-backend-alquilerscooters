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

    public Transaccion crearTransaccion(Long usuarioId, Long viajeId, Double monto) {
        Transaccion transaccion = new Transaccion();
        transaccion.setUsuarioId(usuarioId);
        transaccion.setViajeId(viajeId);
        transaccion.setMonto(monto);
        transaccion.setFecha(LocalDateTime.now());
        return transaccionRepository.save(transaccion);
    }

    public List<Transaccion> obtenerTransaccionesPorUsuario(Long usuarioId) {
        return transaccionRepository.findByUsuarioId(usuarioId);
    }

    public Optional<Transaccion> obtenerTransaccionPorId(Long id) {
        return transaccionRepository.findById(id);
    }

    public void eliminarTransaccion(Long id) {
        transaccionRepository.deleteById(id);
    }
}
