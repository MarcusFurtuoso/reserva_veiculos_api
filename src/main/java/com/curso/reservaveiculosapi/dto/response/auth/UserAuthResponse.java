package com.curso.reservaveiculosapi.dto.response.auth;

public record UserAuthResponse(
        String authorities,
        Long id,
        String nome,
        String login
) {
    public static UserAuthResponse toResponse(String authorities, Long id, String nome, String login) {
        return new UserAuthResponse(
                authorities,
                id,
                nome,
                login
        );
    }
}
