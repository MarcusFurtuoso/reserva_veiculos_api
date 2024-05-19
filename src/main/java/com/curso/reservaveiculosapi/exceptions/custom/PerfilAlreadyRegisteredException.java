package com.curso.reservaveiculosapi.exceptions.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PerfilAlreadyRegisteredException extends RuntimeException {

    public PerfilAlreadyRegisteredException(String message) {
        super(message);
    }
}
