package com.curso.reservaveiculosapi.controller;

import com.curso.reservaveiculosapi.dto.request.usuario.AuthenticationRequest;
import com.curso.reservaveiculosapi.dto.request.usuario.RegisterRequest;
import com.curso.reservaveiculosapi.dto.response.usuario.LoginResponse;
import com.curso.reservaveiculosapi.dto.response.usuario.RegisterResponse;
import com.curso.reservaveiculosapi.service.impl.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Serviços para gerenciar a autenticação do usuário")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthorizationService authorizationService;

    @Operation(summary = "Autenticação do usuário")
    @ApiResponse(responseCode = "200",
            description = "Usuário autenticado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponse.class)))
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid AuthenticationRequest request) {
        return authorizationService.login(request);
    }

    @Operation(summary = "Cadastro do usuário")
    @ApiResponse(responseCode = "201",
            description = "Usuário cadastrado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RegisterResponse.class)))
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody @Valid RegisterRequest request) {
        return authorizationService.register(request);
    }

}
