package com.example.alquiler_scooters.recompensa.domain;

import com.example.alquiler_scooters.recompensa.infrastructure.RecompensaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecompensaService {
    private final RecompensaRepository recompensaRepository;

    @Autowired
    public RecompensaService(RecompensaRepository recompensaRepository) {
        this.recompensaRepository = recompensaRepository;
    }

    public List<Recompensa> obtenerTodasLasRecompensas() {
        return recompensaRepository.findAll();
    }

    public Recompensa guardarRecompensa(Recompensa recompensa) {
        return recompensaRepository.save(recompensa);
    }

    public Optional<Recompensa> obtenerRecompensaPorId(Long id) {
        return recompensaRepository.findById(id);
    }


    public void eliminarRecompensa(Long id) {
        recompensaRepository.deleteById(id);
    }
}
