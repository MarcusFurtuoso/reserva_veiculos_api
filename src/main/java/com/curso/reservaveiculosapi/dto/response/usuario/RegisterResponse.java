package com.curso.reservaveiculosapi.dto.response.usuario;

import com.curso.reservaveiculosapi.entity.UsuarioEntity;

import java.io.Serializable;
import java.util.List;

public record RegisterResponse(
        UsuarioResponse usuario
) implements Serializable {
    public static RegisterResponse toResponse(UsuarioEntity usuarioEntity) {
        return new RegisterResponse(
                UsuarioResponse.toResponse(usuarioEntity)
        );
    }

}
