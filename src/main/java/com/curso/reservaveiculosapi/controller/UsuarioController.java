package com.curso.reservaveiculosapi.controller;

import com.curso.reservaveiculosapi.dto.request.usuario.UsuarioToPerfilRequest;
import com.curso.reservaveiculosapi.dto.request.usuario.RegisterRequest;
import com.curso.reservaveiculosapi.dto.request.usuario.UsuarioUpdateRequest;
import com.curso.reservaveiculosapi.dto.response.usuario.UsuarioToPerfilResponse;
import com.curso.reservaveiculosapi.dto.response.usuario.UsuarioResponse;
import com.curso.reservaveiculosapi.dto.response.veiculo.VeiculoResponse;
import com.curso.reservaveiculosapi.service.impl.UsuarioService;
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
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Serviços para gerenciar os usuários")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Lista todos os usuários")
    @ApiResponse(responseCode = "200",
            description = "Usuários listados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponse.class)))
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponse> listAll() {
        return usuarioService.listAll();
    }

    @Operation(summary = "Lista todos os veículos associados ao usuário especificado pelo ID")
    @ApiResponse(responseCode = "200",
            description = "Veículos listados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VeiculoResponse.class)))
    @GetMapping("/veiculos")
    @ResponseStatus(HttpStatus.OK)
    public List<VeiculoResponse> listAllVeiculosByUsuario(@RequestParam Long usuarioId) {
        return usuarioService.listAllVeiculosByUsuario(usuarioId);
    }

    @Operation(summary = "Adiciona um perfil ao usuário")
    @ApiResponse(responseCode = "201",
            description = "Perfil adicionado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioToPerfilResponse.class)))
    @PostMapping("/add-perfil")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioToPerfilResponse addUsuarioToPerfil(@RequestBody @Valid UsuarioToPerfilRequest request) {
        return usuarioService.addUsuarioToPerfil(request);
    }

    @Operation(summary = "Atualiza um usuário")
    @ApiResponse(responseCode = "200",
            description = "Usuário atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponse.class)))
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse update(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioUpdateRequest request) {
        return usuarioService.update(usuarioId, request);
    }

    @Operation(summary = "Busca um usuário pelo ID")
    @ApiResponse(responseCode = "200",
            description = "Usuário encontrado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponse.class)))
    @GetMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse findById(@PathVariable Long usuarioId) {
        return usuarioService.findById(usuarioId);
    }

    @Operation(summary = "Busca um usuário pelo login")
    @ApiResponse(responseCode = "200",
            description = "Usuário encontrado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponse.class)))
    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse findByLogin(@RequestParam String login) {
        return usuarioService.findByLogin(login);
    }

    @Operation(summary = "Deleta um usuário")
    @ApiResponse(responseCode = "204",
            description = "Usuário deletado com sucesso")
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long usuarioId) {
        usuarioService.delete(usuarioId);
    }

}
