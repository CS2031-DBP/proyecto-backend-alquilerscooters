package com.example.alquiler_scooters.viaje.domain;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.scooter.infrastructure.ScooterRepository;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import com.example.alquiler_scooters.viaje.dto.ScooterSimpleDTO;
import com.example.alquiler_scooters.viaje.dto.UsuarioSimpleDTO;
import com.example.alquiler_scooters.viaje.dto.ViajeDTO;
import com.example.alquiler_scooters.viaje.exceptions.NoViajesFoundException;
import com.example.alquiler_scooters.viaje.infrastructure.ViajeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
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
    public String save(ViajeDTO viajeDTO) {
        Viaje viaje = new Viaje();

        // Establecer la hora de inicio al momento actual si no se proporciona en el DTO
        if (viajeDTO.getHoraInicio() == null) {
            viaje.setHoraInicio(LocalDateTime.now());
        } else {
            viaje.setHoraInicio(viajeDTO.getHoraInicio());
        }

        // Otras asignaciones
        viaje.setHoraFin(viajeDTO.getHoraFin());
        viaje.setPuntoPartida(viajeDTO.getPuntoPartida());
        viaje.setPuntoFin(viajeDTO.getPuntoFin());
        viaje.setCosto(viajeDTO.getCosto());
        viaje.setEstado(Viaje.EstadoViaje.ACTIVO);  // Asignar estado del viaje como ACTIVO

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
            return "Viaje creado con éxito con el ID: " + savedViaje.getId();
        } else {
            throw new RuntimeException("Usuario o Scooter no encontrado");
        }
    }

    @Transactional
    public String finalizarViaje(UUID id) {
        return viajeRepository.findById(id).map(viaje -> {
            LocalDateTime horaFin = LocalDateTime.now();
            String puntoFin = viaje.getScooter().getUbicacionActual();

            // Lanzar una excepción si horaInicio es nula
            if (viaje.getHoraInicio() == null) {
                throw new RuntimeException("La hora de inicio del viaje es nula.");
            }

            viaje.setHoraFin(horaFin);
            viaje.setPuntoFin(puntoFin);

            // Calcular el costo del viaje
            Duration duration = Duration.between(viaje.getHoraInicio(), viaje.getHoraFin());
            long minutes = duration.toSeconds();
            double costo = 0.5 * minutes;
            viaje.setCosto(costo);

            // Actualizar el estado del scooter a DISPONIBLE
            Scooter scooter = viaje.getScooter();
            scooter.setEstado(Scooter.EstadoScooter.DISPONIBLE);
            scooterRepository.save(scooter);

            // Actualizar el estado del viaje a FINALIZADO
            viaje.setEstado(Viaje.EstadoViaje.FINALIZADO);

            viajeRepository.save(viaje);
            return "Viaje finalizado correctamente.";
        }).orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<ViajeDTO> findAll() {
        return viajeRepository.findAll().stream()
                .map(viaje -> {
                    ViajeDTO viajeDTO = modelMapper.map(viaje, ViajeDTO.class);
                    UsuarioSimpleDTO usuarioDTO = new UsuarioSimpleDTO();
                    usuarioDTO.setId(viaje.getUsuario().getId());
                    viajeDTO.setUsuario(usuarioDTO);

                    ScooterSimpleDTO scooterDTO = new ScooterSimpleDTO();
                    scooterDTO.setId(viaje.getScooter().getId());
                    viajeDTO.setScooter(scooterDTO);

                    return viajeDTO;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ViajeDTO> findByUsuarioId(Long usuarioId) {
        List<Viaje> viajes = viajeRepository.findByUsuarioId(usuarioId);
        if (viajes.isEmpty()) {
            throw new NoViajesFoundException("No tiene viajes registrados este id: " + usuarioId);
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

    @Transactional(readOnly = true)
    public List<ViajeDTO> findByUsuarioEmail(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            List<Viaje> viajes = viajeRepository.findByUsuarioId(usuarioOpt.get().getId());
            if (viajes.isEmpty()) {
                // Return an empty list to indicate no trips found
                return Collections.emptyList();
            }
            return viajes.stream()
                    .map(viaje -> modelMapper.map(viaje, ViajeDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw new NoViajesFoundException("No se encontró un usuario con el correo: " + email);
        }
    }


    @Transactional
    public String saveViajeWithEmailAndScooterId(String email, UUID scooterId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        Optional<Scooter> scooterOpt = scooterRepository.findById(scooterId);

        if (usuarioOpt.isPresent() && scooterOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Scooter scooter = scooterOpt.get();

            scooter.setEstado(Scooter.EstadoScooter.EN_USO);

            Viaje viaje = new Viaje();
            viaje.setUsuario(usuario);
            viaje.setScooter(scooter);
            viaje.setHoraInicio(LocalDateTime.now());
            viaje.setPuntoPartida(scooter.getUbicacionActual());
            viaje.setEstado(Viaje.EstadoViaje.ACTIVO);

            scooterRepository.save(scooter);
            Viaje savedViaje = viajeRepository.save(viaje);

            return "Viaje creado con éxito con el ID: " + savedViaje.getId();
        } else {
            throw new RuntimeException("Usuario o Scooter no encontrado");
        }
    }
}
