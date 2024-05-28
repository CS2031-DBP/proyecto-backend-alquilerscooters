package com.example.alquiler_scooters.metodo_pago.domain;

import com.example.alquiler_scooters.metodo_pago.infrastructure.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TarjetaService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    public Tarjeta save(Tarjeta tarjeta) {
        return tarjetaRepository.save(tarjeta);
    }

    public List<Tarjeta> findAll() {
        return tarjetaRepository.findAll();
    }

    public Optional<Tarjeta> findById(UUID id) {
        return tarjetaRepository.findById(id);
    }

    public Tarjeta updateTarjeta(UUID id, Tarjeta tarjetaDetalles) {
        return tarjetaRepository.findById(id).map(tarjeta -> {
            tarjeta.setUsuario(tarjetaDetalles.getUsuario());
            tarjeta.setNumeroTarjeta(tarjetaDetalles.getNumeroTarjeta());
            tarjeta.setFechaExpiracion(tarjetaDetalles.getFechaExpiracion());
            tarjeta.setCsc(tarjetaDetalles.getCsc());
            tarjeta.setTitular(tarjetaDetalles.getTitular());
            return tarjetaRepository.save(tarjeta);
        }).orElseThrow(() -> new RuntimeException("Tarjeta no encontrada con id: " + id));
    }

    public void deleteById(UUID id) {
        tarjetaRepository.deleteById(id);
    }
}