package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.dto.request.usuario.AuthenticationRequest;
import com.curso.reservaveiculosapi.dto.request.usuario.RegisterRequest;
import com.curso.reservaveiculosapi.dto.response.usuario.LoginResponse;
import com.curso.reservaveiculosapi.dto.response.usuario.RegisterResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAuthorizationService extends UserDetailsService {

    LoginResponse login(AuthenticationRequest request);

    RegisterResponse register (RegisterRequest request);
}
