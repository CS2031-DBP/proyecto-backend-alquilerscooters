package com.example.alquiler_scooters.eventos;

import com.example.alquiler_scooters.config.EmailService;
import com.example.alquiler_scooters.facturacion.domain.Facturacion;
import com.example.alquiler_scooters.facturacion.infrastructure.FacturacionRepository;
import com.example.alquiler_scooters.scooter.domain.ScooterService;
import com.example.alquiler_scooters.viaje.domain.Viaje;
import com.example.alquiler_scooters.viaje.infrastructure.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class SendFacturaListener {

    @Autowired
    private EmailService emailService;

    @Autowired
    FacturacionRepository facturacionRepository;

    @Autowired
    ViajeRepository viajeRepository;

    @EventListener
    @Async
    public void handleSendFacturaEvent(SendFacturaEvent sendFacturaEvent) {
        Facturacion facturacion = facturacionRepository.findById(sendFacturaEvent.getFacturacion().getId()).orElse(null);
        if (facturacion != null) {
            Viaje viaje = viajeRepository.findById(facturacion.getViaje().getId()).orElse(null);
            if (viaje != null) {
                String message = "Boleta de venta: \n" +
                        "ID: " + facturacion.getId() + "\n" +
                        "Nombre: " + facturacion.getUsuario().getNombre() + "\n" +
                        "ID del usuario: " + facturacion.getUsuario().getId() + "\n" +
                        "Monto: " + viaje.getCosto() + "\n" +
                        "ID del viaje: " + viaje.getId();
                emailService.sendEmail(sendFacturaEvent.getEmail(), "Boleta de Venta Electrónica", message);
            }
        }
    }
}

