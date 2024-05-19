package com.curso.reservaveiculosapi.dto.response.usuario;

import com.curso.reservaveiculosapi.entity.PerfilEntity;
import com.curso.reservaveiculosapi.entity.UsuarioEntity;

import java.io.Serializable;

public record UsuarioToPerfilResponse(
        String usuarioLogin,
        String perfilNome
) implements Serializable {
    public static UsuarioToPerfilResponse toResponse(UsuarioEntity usuarioEntity, PerfilEntity perfilEntity) {
        return new UsuarioToPerfilResponse(
                usuarioEntity.getLogin(),
                perfilEntity.getNome()
        );
    }
}
