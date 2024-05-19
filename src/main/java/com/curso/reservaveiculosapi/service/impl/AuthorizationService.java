package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.dto.request.usuario.AuthenticationRequest;
import com.curso.reservaveiculosapi.dto.request.usuario.RegisterRequest;
import com.curso.reservaveiculosapi.dto.response.usuario.LoginResponse;
import com.curso.reservaveiculosapi.dto.response.usuario.RegisterResponse;
import com.curso.reservaveiculosapi.entity.UsuarioEntity;
import com.curso.reservaveiculosapi.exceptions.custom.EmailAlreadyRegisteredException;
import com.curso.reservaveiculosapi.repository.UsuarioRepository;
import com.curso.reservaveiculosapi.service.IAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements IAuthorizationService {

    private final ApplicationContext context;

    private final UsuarioRepository usuarioRepository;

    private final TokenService tokenService;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login);
    }

    @Override
    public LoginResponse login(AuthenticationRequest request) {
        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UsuarioEntity) auth.getPrincipal());

        return new LoginResponse(token);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (this.usuarioRepository.findByLogin(request.login()) != null) {
            throw new EmailAlreadyRegisteredException("Email already registered");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.senha());
        UsuarioEntity newUser = UsuarioEntity.builder()
                .nome(request.nome())
                .login(request.login())
                .senha(encryptedPassword)
                .build();

        UsuarioEntity userSave = this.usuarioRepository.save(newUser);

        return RegisterResponse.toResponse(userSave);
    }
}
