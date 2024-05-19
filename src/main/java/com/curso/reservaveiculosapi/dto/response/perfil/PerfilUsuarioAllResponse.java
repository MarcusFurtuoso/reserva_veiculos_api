package com.curso.reservaveiculosapi.dto.response.perfil;

import com.curso.reservaveiculosapi.dto.response.usuario.UsuarioResponse;

import java.io.Serializable;
import java.util.List;

public record PerfilUsuarioAllResponse(
    String nome,
    List<UsuarioResponse> usuarios
) implements Serializable {
    public static PerfilUsuarioAllResponse toResponse(String nome, List<UsuarioResponse> usuarios) {
        return new PerfilUsuarioAllResponse(
            nome,
            usuarios
        );
    }

}
