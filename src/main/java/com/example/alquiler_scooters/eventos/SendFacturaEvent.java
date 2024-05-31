package com.example.alquiler_scooters.eventos;

import com.example.alquiler_scooters.facturacion.domain.Facturacion;
import org.springframework.context.ApplicationEvent;

public class SendFacturaEvent extends ApplicationEvent {
    private String email;
    private Facturacion facturacion;

    public SendFacturaEvent(Object source, String email, Facturacion facturacion) {
        super(source);
        this.email = email;
        this.facturacion = facturacion;
    }

    public String getEmail() {
        return email;
    }

    public Facturacion getFacturacion() {
        return facturacion;
    }
}
