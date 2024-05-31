package com.example.alquiler_scooters.facturacion.domain;

import com.example.alquiler_scooters.eventos.SendFacturaEvent;
import com.example.alquiler_scooters.facturacion.infrastructure.FacturacionRepository;
import com.example.alquiler_scooters.metodo_pago.domain.Tarjeta;
import com.example.alquiler_scooters.metodo_pago.domain.TarjetaDTO;
import com.example.alquiler_scooters.metodo_pago.infrastructure.TarjetaRepository;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.domain.UsuarioDto;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import com.example.alquiler_scooters.viaje.domain.Viaje;
import com.example.alquiler_scooters.viaje.dto.FacturaViajeDTO;
import com.example.alquiler_scooters.viaje.infrastructure.ViajeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FacturacionService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private FacturacionRepository facturacionRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private TarjetaRepository tarjetaRepository;
    public Facturacion save(FacturacionRequest facturacionRequest) {
        // Obtener las entidades a partir del DTO
        Usuario usuario = usuarioRepository.findById(facturacionRequest.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Viaje viaje = viajeRepository.findById(facturacionRequest.getViajeId())
                .orElseThrow(() -> new EntityNotFoundException("Viaje no encontrado"));

        Tarjeta tarjeta = tarjetaRepository.findById(facturacionRequest.getTarjetaId())
                .orElseThrow(() -> new EntityNotFoundException("Tarjeta no encontrada"));

        Facturacion facturacion = new Facturacion();
        facturacion.setUsuario(usuario);
        facturacion.setViaje(viaje);
        facturacion.setTarjeta(tarjeta);

        facturacionRepository.save(facturacion);

        // Publicar el evento de enviar factura
        applicationEventPublisher.publishEvent(new SendFacturaEvent(this, usuario.getEmail(), facturacion));

        return facturacion;
    }


    public List<FacturacionDto> findAll() {
        List<Facturacion> facturaciones = facturacionRepository.findAll();
        return facturaciones.stream()
                .map(facturacion -> {
                    FacturacionDto facturacionDto = mapper.map(facturacion, FacturacionDto.class);
                    facturacionDto.setUsuario(mapper.map(facturacion.getUsuario(), UsuarioDto.class));
                                                            facturacionDto.setViaje(mapper.map(facturacion.getViaje(), FacturaViajeDTO.class));
                    return facturacionDto;
                })
                .collect(Collectors.toList());
    }

    public Optional<Facturacion> findById(UUID id) {
        return facturacionRepository.findById(id);
    }

    public Facturacion updateFacturacion(UUID id, Facturacion facturacionDetalles) {
        return facturacionRepository.findById(id).map(facturacion -> {
            facturacion.setUsuario(facturacionDetalles.getUsuario());
            facturacion.setViaje(facturacionDetalles.getViaje());
            facturacion.setTarjeta(facturacionDetalles.getTarjeta());
            return facturacionRepository.save(facturacion);
        }).orElseThrow(() -> new RuntimeException("Facturación no encontrada con id: " + id));
    }

    public void deleteById(UUID id) {
        facturacionRepository.deleteById(id);
    }
}