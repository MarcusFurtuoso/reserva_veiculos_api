package com.curso.reservaveiculosapi.controller;

import com.curso.reservaveiculosapi.dto.request.perfil.PerfilRequest;
import com.curso.reservaveiculosapi.dto.response.perfil.PerfilResponse;
import com.curso.reservaveiculosapi.dto.response.perfil.PerfilUsuarioAllResponse;
import com.curso.reservaveiculosapi.service.impl.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfil")
@RequiredArgsConstructor
@Tag(name = "Perfil", description = "Serviços para gerenciar os perfis de usuários")
@CrossOrigin(origins = "http://localhost:4200")
public class PerfilController {

    private final PerfilService perfilService;

    @Operation(summary = "Lista todos os perfis")
    @ApiResponse(responseCode = "200",
            description = "Perfis listados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PerfilResponse.class)))
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PerfilResponse> listAll() {
        return perfilService.listAll();
    }

    @Operation(summary = "Lista todos os usuários associados ao perfil especificado pelo nome")
    @ApiResponse(responseCode = "200",
            description = "Uusários listados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PerfilUsuarioAllResponse.class)))
    @GetMapping("/usuarios")
    @ResponseStatus(HttpStatus.OK)
    public PerfilUsuarioAllResponse listAllUsuariosByPerfilNome(@RequestParam String nome) {
        return perfilService.listAllUsuariosByPerfilNome(nome);
    }

    @Operation(summary = "Cadastra um novo perfil")
    @ApiResponse(responseCode = "201",
            description = "Perfil cadastrado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PerfilResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PerfilResponse register(@RequestBody @Valid PerfilRequest request) {
        return perfilService.register(request);
    }

    @Operation(summary = "Atualiza o nome de um perfil")
    @ApiResponse(responseCode = "200",
            description = "Perfil atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PerfilResponse.class)))
    @PutMapping("/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public PerfilResponse update(@PathVariable Long perfilId, @RequestBody @Valid PerfilRequest request) {
        return perfilService.update(perfilId, request);
    }

    @Operation(summary = "Deleta um perfil")
    @ApiResponse(responseCode = "204",
            description = "Perfil deletado com sucesso")
    @DeleteMapping("/{perfilId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long perfilId) {
        perfilService.delete(perfilId);
    }
}
