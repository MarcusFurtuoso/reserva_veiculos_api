package com.curso.reservaveiculosapi.dto.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioToPerfilRequest(
        @NotNull @NotBlank @Email String usuarioLogin,
        @NotNull @NotBlank String perfil
) {
}
