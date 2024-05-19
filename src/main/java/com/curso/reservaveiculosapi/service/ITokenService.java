package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.entity.UsuarioEntity;

public interface ITokenService {

    String generateToken(UsuarioEntity usuario);

    String validateToken(String token);

}
