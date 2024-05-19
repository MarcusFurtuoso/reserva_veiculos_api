package com.curso.reservaveiculosapi.exceptions.custom;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiAuthException extends AuthenticationException {

    public ApiAuthException(String msg) {
        super(msg);
    }
}