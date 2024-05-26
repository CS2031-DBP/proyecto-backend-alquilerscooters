package com.example.alquiler_scooters.recompensa.domain;

import com.example.alquiler_scooters.recompensa.infrastructure.RecompensaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecompensaService {
    @Autowired
    private RecompensaRepository recompensaRepository;

    public List<Recompensa> findAll() {
        return recompensaRepository.findAll();
    }

    public Optional<Recompensa> findById(Long id) {
        return recompensaRepository.findById(id);
    }

    public Recompensa save(Recompensa recompensa) {
        return recompensaRepository.save(recompensa);
    }

    public Recompensa updateRecompensa(Long id, Recompensa recompensaDetails) {
        return recompensaRepository.findById(id).map(recompensa -> {
            recompensa.setDescripcion(recompensaDetails.getDescripcion());
            recompensa.setFecha(recompensaDetails.getFecha());
            recompensa.setUsuarioId(recompensaDetails.getUsuarioId());
            return recompensaRepository.save(recompensa);
        }).orElse(null);
    }

    public void deleteById(Long id) {
        recompensaRepository.deleteById(id);
    }
}
