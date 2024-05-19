package com.curso.reservaveiculosapi.dto.response.usuario;

import com.curso.reservaveiculosapi.entity.UsuarioEntity;

import java.io.Serializable;

public record RegisterResponse(
        Long id,
        String nome,
        String login
) implements Serializable {
    public static RegisterResponse toResponse(UsuarioEntity usuarioEntity) {
        return new RegisterResponse(
                usuarioEntity.getId(),
                usuarioEntity.getNome(),
                usuarioEntity.getLogin()
        );
    }

}
