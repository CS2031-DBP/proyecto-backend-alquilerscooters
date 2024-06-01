package com.example.alquiler_scooters.recompensa.domain;

import com.example.alquiler_scooters.estacionamiento.domain.Estacionamiento;
import com.example.alquiler_scooters.estacionamiento.infrastructure.EstacionamientoRepository;
import com.example.alquiler_scooters.excepciones.ResourceNotFoundException;
import com.example.alquiler_scooters.recompensa.infrastructure.RecompensaRepository;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import com.example.alquiler_scooters.viaje.domain.Viaje;
import com.example.alquiler_scooters.viaje.infrastructure.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecompensaService {
    @Autowired
    private RecompensaRepository recompensaRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstacionamientoRepository estacionamientoRepository;

    public List<Recompensa> getAllRecompensas() {
        return recompensaRepository.findAll();
    }

    @Autowired
    public RecompensaService(RecompensaRepository recompensaRepository) {
        this.recompensaRepository = recompensaRepository;
    }

    public Recompensa getRecompensaById(Long id) {
        return recompensaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recompensa no encontrada con ID: " + id));
    }

    public List<Recompensa> getRecompensasByUsuarioId(Long usuarioId) {
        return recompensaRepository.findByUsuarioId(usuarioId);
    }

    public Recompensa createRecompensa(UUID viajeId, String nombre, String descripcion) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new ResourceNotFoundException("Viaje no encontrado con ID: " + viajeId));

        Usuario usuario = viaje.getUsuario();
        List<Estacionamiento> estacionamientos = estacionamientoRepository.findAll();

        Estacionamiento estacionamiento = estacionamientos.stream()
                .filter(e -> e.getUbicacion().equals(viaje.getPuntoFin()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Estacionamiento no encontrado para la ubicación final del viaje"));

        Recompensa recompensa = new Recompensa();
        recompensa.setViaje(viaje);
        recompensa.setUsuario(usuario);
        recompensa.setEstacionamiento(estacionamiento);
        recompensa.setNombre(nombre);
        recompensa.setDescripcion(descripcion);
        recompensa.setFecha(LocalDateTime.now());

        return recompensaRepository.save(recompensa);
    }

    public void deleteById(Long id) {
        Optional<Recompensa> recompensaOptional = recompensaRepository.findById(id);
        if (recompensaOptional.isPresent()) {
            recompensaRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Recompensa no encontrada con ID: " + id);
        }
    }
}