package com.curso.reservaveiculosapi.controller;

import com.curso.reservaveiculosapi.dto.request.imagem.ImagemRequest;
import com.curso.reservaveiculosapi.dto.request.imagem.ImagemUpdateRequest;
import com.curso.reservaveiculosapi.dto.response.imagem.ImagemResponse;
import com.curso.reservaveiculosapi.service.impl.ImagemVeiculoService;
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
@RequestMapping("/api/imagem-veiculo")
@RequiredArgsConstructor
@Tag(name = "Imagem do Veículo", description = "Serviços para gerenciar as imagens dos veículos")
@CrossOrigin(origins = "http://localhost:4200")
public class ImagemVeiculoController {

    private final ImagemVeiculoService imagemVeiculoService;

    @Operation(summary = "Cadastra uma imagem para um veículo")
    @ApiResponse(responseCode = "201",
            description = "Cadastro da imagem realizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ImagemResponse.class)))
    @PostMapping("/{veiculoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ImagemResponse register(@PathVariable Long veiculoId, @RequestBody @Valid ImagemRequest request) {
        return imagemVeiculoService.register(veiculoId, request);
    }

    @Operation(summary = "Atualiza ou adiciona uma imagem de um veículo")
    @ApiResponse(responseCode = "200",
            description = "Imagem atualizada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ImagemResponse.class)))
    @PutMapping("/{veiculoId}")
    @ResponseStatus(HttpStatus.OK)
    public ImagemResponse update(@PathVariable Long veiculoId, @RequestBody @Valid ImagemUpdateRequest request) {
        return imagemVeiculoService.update(veiculoId, request);
    }

    @Operation(summary = "Deleta uma imagem de um veículo")
    @ApiResponse(responseCode = "204",
            description = "Imagem deletada com sucesso")
    @DeleteMapping("/{imagemVeiculoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long imagemVeiculoId) {
        imagemVeiculoService.delete(imagemVeiculoId);
    }

}
