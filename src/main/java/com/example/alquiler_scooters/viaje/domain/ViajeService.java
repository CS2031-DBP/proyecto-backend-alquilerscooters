package com.example.alquiler_scooters.viaje.domain;

import com.example.alquiler_scooters.scooter.domain.Scooter;
import com.example.alquiler_scooters.scooter.infrastructure.ScooterRepository;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import com.example.alquiler_scooters.viaje.dto.ViajeDTO;
import com.example.alquiler_scooters.viaje.infrastructure.ViajeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ViajeDTO save(ViajeDTO viajeDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(viajeDTO.getUsuario().getId());
        Optional<Scooter> scooterOpt = scooterRepository.findById(viajeDTO.getScooter().getId());

        if (usuarioOpt.isPresent() && scooterOpt.isPresent()) {
            Viaje viaje = modelMapper.map(viajeDTO, Viaje.class);
            viaje.setUsuario(usuarioOpt.get());
            viaje.setScooter(scooterOpt.get());
            Viaje savedViaje = viajeRepository.save(viaje);
            return modelMapper.map(savedViaje, ViajeDTO.class);
        } else {
            throw new RuntimeException("Usuario o Scooter no encontrado");
        }
    }

    public List<ViajeDTO> findAll() {
        return viajeRepository.findAll().stream()
                .map(viaje -> modelMapper.map(viaje, ViajeDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<ViajeDTO> findById(UUID id) {
        return viajeRepository.findById(id)
                .map(viaje -> modelMapper.map(viaje, ViajeDTO.class));
    }

    public void deleteById(UUID id) {
        viajeRepository.deleteById(id);
    }
}
