package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.dto.response.auth.UserAuthResponse;
import com.curso.reservaveiculosapi.entity.UsuarioEntity;

public interface ITokenService {

    String generateToken(UsuarioEntity usuario, UserAuthResponse user);

    String validateToken(String token);

    Long getUserIdFromJWT(String token);

}
