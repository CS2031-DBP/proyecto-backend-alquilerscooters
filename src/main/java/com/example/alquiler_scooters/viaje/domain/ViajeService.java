package com.example.alquiler_scooters.viaje.domain;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.scooter.infrastructure.ScooterRepository;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import com.example.alquiler_scooters.viaje.dto.ViajeDTO;
import com.example.alquiler_scooters.viaje.exceptions.NoViajesFoundException;
import com.example.alquiler_scooters.viaje.infrastructure.ViajeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ScooterRepository scooterRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ViajeDTO save(ViajeDTO viajeDTO) {
        Viaje viaje = new Viaje();
        viaje.setHoraInicio(viajeDTO.getHoraInicio());
        viaje.setHoraFin(viajeDTO.getHoraFin());
        viaje.setPuntoPartida(viajeDTO.getPuntoPartida());
        viaje.setPuntoFin(viajeDTO.getPuntoFin());
        viaje.setCosto(viajeDTO.getCosto());

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(viajeDTO.getUsuario().getId());
        Optional<Scooter> scooterOpt = scooterRepository.findById(viajeDTO.getScooter().getId());

        if (usuarioOpt.isPresent() && scooterOpt.isPresent()) {
            Scooter scooter = scooterOpt.get();
            scooter.setEstado(Scooter.EstadoScooter.EN_USO);
            viaje.setPuntoPartida(scooter.getUbicacionActual());
            scooterRepository.save(scooter);

            viaje.setUsuario(usuarioOpt.get());
            viaje.setScooter(scooter);

            Viaje savedViaje = viajeRepository.save(viaje);
            return modelMapper.map(savedViaje, ViajeDTO.class);
        } else {
            throw new RuntimeException("Usuario o Scooter no encontrado");
        }
    }

    @Transactional
    public ViajeDTO finalizarViaje(UUID id) {
        return viajeRepository.findById(id).map(viaje -> {
            LocalDateTime horaFin = LocalDateTime.now();
            String puntoFin = viaje.getScooter().getUbicacionActual();

            viaje.setHoraFin(horaFin);
            viaje.setPuntoFin(puntoFin);

            // Calcular el costo del viaje
            Duration duration = Duration.between(viaje.getHoraInicio(), viaje.getHoraFin());
            long minutes = duration.toMinutes();
            double costo = 0.5 * minutes;
            viaje.setCosto(costo);

            // Actualizar el estado del scooter a DISPONIBLE
            Scooter scooter = viaje.getScooter();
            scooter.setEstado(Scooter.EstadoScooter.DISPONIBLE);
            scooterRepository.save(scooter);

            Viaje updatedViaje = viajeRepository.save(viaje);
            return modelMapper.map(updatedViaje, ViajeDTO.class);
        }).orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + id));
    }



    @Transactional(readOnly = true)
    public List<ViajeDTO> findAll() {
        return viajeRepository.findAll().stream()
                .map(viaje -> modelMapper.map(viaje, ViajeDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ViajeDTO> findByUsuarioId(Long usuarioId) {
        List<Viaje> viajes = viajeRepository.findByUsuarioId(usuarioId);
        if (viajes.isEmpty()) {
            throw new NoViajesFoundException("No tiene viajes registrados este id:  " + usuarioId);
        }
        return viajes.stream()
                .map(viaje -> modelMapper.map(viaje, ViajeDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ViajeDTO> findById(UUID id) {
        return viajeRepository.findById(id)
                .map(viaje -> modelMapper.map(viaje, ViajeDTO.class));
    }

    @Transactional
    public void deleteById(UUID id) {
        viajeRepository.deleteById(id);
    }
}