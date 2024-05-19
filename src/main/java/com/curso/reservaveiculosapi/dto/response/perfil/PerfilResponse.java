package com.curso.reservaveiculosapi.dto.response.perfil;

import com.curso.reservaveiculosapi.entity.PerfilEntity;

import java.io.Serializable;

public record PerfilResponse(
        Long id,
        String nome
) implements Serializable {
    public static PerfilResponse toResponse(PerfilEntity perfilEntity) {
        return new PerfilResponse(
                perfilEntity.getId(),
                perfilEntity.getNome()
        );
    }

}
