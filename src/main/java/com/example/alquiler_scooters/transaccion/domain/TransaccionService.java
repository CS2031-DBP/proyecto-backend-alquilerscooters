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
    public Optional<Transaccion> update(Long id, Transaccion transaccionDetails) {
        return transaccionRepository.findById(id).map(transaccion -> {
            // Actualiza los campos de la transacción con los nuevos valores
            transaccion.setFecha(transaccionDetails.getFecha()); // Actualiza campo 1
            transaccion.setId(transaccionDetails.getId()); // Actualiza campo 2
            transaccion.setMonto(transaccionDetails.getMonto()); // Actualiza campo 1
            transaccion.setViajeId(transaccionDetails.getViajeId());
            transaccion.setUsuarioId(transaccionDetails.getUsuarioId());

            // Guarda los cambios en el repositorio
            return transaccionRepository.save(transaccion);
        });
    }

    public Optional<Transaccion> partialUpdate(Long id, Transaccion transaccionDetails) {
        return transaccionRepository.findById(id).map(transaccion -> {
            if (transaccionDetails.getFecha() != null) {
                transaccion.setFecha(transaccionDetails.getFecha());
            }
            if (transaccionDetails.getId() != null) {
                transaccion.setId(transaccionDetails.getId());
            }
            if (transaccionDetails.getMonto() != null) {
                transaccion.setMonto(transaccionDetails.getMonto());
            }
            if(transaccionDetails.getViajeId() != null){
                transaccion.setViajeId(transaccionDetails.getViajeId());
            }
            if(transaccionDetails.getUsuarioId() !=null){
                transaccion.setUsuarioId(transaccionDetails.getUsuarioId());
            }
            return transaccionRepository.save(transaccion);
        });
    }
}