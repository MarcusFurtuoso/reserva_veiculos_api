package com.curso.reservaveiculosapi.exceptions.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImagemVeiculoIsFullException extends RuntimeException {

    public ImagemVeiculoIsFullException(String message) {
        super(message);
    }
}
