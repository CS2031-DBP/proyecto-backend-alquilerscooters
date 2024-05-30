package com.example.alquiler_scooters.usuario.exceptions;

public class UsuarioException extends RuntimeException {
    private int statusCode;

    public UsuarioException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
