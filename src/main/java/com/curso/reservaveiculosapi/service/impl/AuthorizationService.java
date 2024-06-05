package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.dto.request.usuario.AuthenticationRequest;
import com.curso.reservaveiculosapi.dto.request.usuario.RegisterRequest;
import com.curso.reservaveiculosapi.dto.response.auth.UserAuthResponse;
import com.curso.reservaveiculosapi.dto.response.usuario.LoginResponse;
import com.curso.reservaveiculosapi.dto.response.usuario.RegisterResponse;
import com.curso.reservaveiculosapi.entity.UsuarioEntity;
import com.curso.reservaveiculosapi.exceptions.custom.EmailAlreadyRegisteredException;
import com.curso.reservaveiculosapi.repository.PerfilRepository;
import com.curso.reservaveiculosapi.repository.UsuarioRepository;
import com.curso.reservaveiculosapi.service.IAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements IAuthorizationService {

    private final ApplicationContext context;

    private final UsuarioRepository usuarioRepository;

    private final PerfilRepository perfilRepository;

    private final TokenService tokenService;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDetails user = usuarioRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return user;
    }

    @Override
    public LoginResponse login(AuthenticationRequest request) {
        authenticationManager = context.getBean(AuthenticationManager.class);

        var perfis = perfilRepository.findAll();

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (UsuarioEntity) auth.getPrincipal();

        var authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        UserAuthResponse userAuthResponse =
                UserAuthResponse.toResponse(authorities, user.getId(), user.getNome(), user.getLogin());

        var token = tokenService.generateToken((UsuarioEntity) auth.getPrincipal(), userAuthResponse);

        return LoginResponse.toResponse(token);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (this.usuarioRepository.findByLogin(request.login()) != null) {
            throw new EmailAlreadyRegisteredException("Email já cadastrado");
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
