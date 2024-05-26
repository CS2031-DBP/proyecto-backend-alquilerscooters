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

    public List<Transaccion> findAll() {
        return transaccionRepository.findAll();
    }

    public Optional<Transaccion> findById(Long id) {
        return transaccionRepository.findById(id);
    }

    public Transaccion save(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }

    public void deleteById(Long id) {
        transaccionRepository.deleteById(id);
    }
}