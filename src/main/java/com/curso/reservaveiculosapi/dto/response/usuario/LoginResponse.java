package com.curso.reservaveiculosapi.dto.response.usuario;

import java.io.Serializable;

public record LoginResponse(
        String token
) implements Serializable {
    public static LoginResponse toResponse(String token) {
        return new LoginResponse(
                token
        );
    }
}
