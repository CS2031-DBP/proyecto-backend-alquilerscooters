package com.example.alquiler_scooters.recompensa.domain;

import com.example.alquiler_scooters.recompensa.infrastructure.RecompensaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecompensaService {
    @Autowired
    private RecompensaRepository recompensaRepository;

    public List<Recompensa> getAllRecompensas() {
        return recompensaRepository.findAll();
    }

    public Optional<Recompensa> getRecompensaById(Long id) {
        return recompensaRepository.findById(id);
    }

    public List<Recompensa> getRecompensasByUsuarioId(Long usuarioId) {
        return recompensaRepository.findByUsuarioId(usuarioId);
    }

    public Recompensa createRecompensa(Recompensa recompensa) {
        return recompensaRepository.save(recompensa);
    }

    public void deleteById(Long id) {
        recompensaRepository.deleteById(id);
    }

}
