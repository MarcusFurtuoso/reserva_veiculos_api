package com.curso.reservaveiculosapi.exceptions.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImagemAlreadyRegisteredException extends RuntimeException{

    public ImagemAlreadyRegisteredException(String message) {
        super(message);
    }
}
