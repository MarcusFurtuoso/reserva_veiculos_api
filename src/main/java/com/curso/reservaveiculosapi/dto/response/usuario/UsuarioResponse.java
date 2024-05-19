package com.curso.reservaveiculosapi.dto.response.usuario;

import com.curso.reservaveiculosapi.entity.UsuarioEntity;

import java.io.Serializable;

public record UsuarioResponse(
        Long id,
        String nome,
        String login
) implements Serializable {
    public static UsuarioResponse toResponse(UsuarioEntity usuarioEntity) {
        return new UsuarioResponse(
                usuarioEntity.getId(),
                usuarioEntity.getNome(),
                usuarioEntity.getLogin()
        );
    }
}
