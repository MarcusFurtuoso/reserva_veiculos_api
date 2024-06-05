package com.curso.reservaveiculosapi.controller;

import com.curso.reservaveiculosapi.dto.request.veiculo.VeiculoFilter;
import com.curso.reservaveiculosapi.dto.request.veiculo.VeiculoRequest;
import com.curso.reservaveiculosapi.dto.response.veiculo.VeiculoImagemResponse;
import com.curso.reservaveiculosapi.dto.response.veiculo.VeiculoResponse;
import com.curso.reservaveiculosapi.service.impl.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculo")
@RequiredArgsConstructor
@Tag(name = "Veiculo", description = "Serviços para gerenciar os veículos")
@CrossOrigin(origins = "http://localhost:4200")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @Operation(summary = "Lista todos os veículos")
    @ApiResponse(responseCode = "200",
            description = "Veículos listados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VeiculoResponse.class)))
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VeiculoResponse> listAll() {
        return veiculoService.listAll();
    }

    @GetMapping("/all-paginated")
    public Page<VeiculoResponse> listAllPaginated(@ParameterObject Pageable pageable, VeiculoFilter veiculoFilter) {
        return veiculoService.listAllPaginated(pageable, veiculoFilter);
    }

    @GetMapping("/carros")
    @ResponseStatus(HttpStatus.OK)
    public List<VeiculoResponse> listAllCars() {
        return veiculoService.listAllCars();
    }

    @GetMapping("/motos")
    @ResponseStatus(HttpStatus.OK)
    public List<VeiculoResponse> listAllMotorcycles() {
        return veiculoService.listAllMotorcycles();
    }

    @GetMapping("/caminhoes")
    public List<VeiculoResponse> listAllTrucks() {
        return veiculoService.listAllTrucks();
    }

    @GetMapping("/imagens")
    @ResponseStatus(HttpStatus.OK)
    public List<VeiculoImagemResponse> getImagens(@RequestParam Long veiculoId) {
        return veiculoService.getImagens(veiculoId);
    }

    @Operation(summary = "Busca um veículo pelo ID")
    @ApiResponse(responseCode = "200",
            description = "Veículo encontrado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VeiculoResponse.class)))
    @GetMapping("/{veiculoId}")
    @ResponseStatus(HttpStatus.OK)
    public VeiculoResponse findById(@PathVariable Long veiculoId) {
        return veiculoService.findById(veiculoId);
    }

    @Operation(summary = "Cadastra um veículo")
    @ApiResponse(responseCode = "201",
            description = "Veículo cadastrado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VeiculoResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoResponse register(@RequestBody @Valid VeiculoRequest veiculoRequest) {
        return veiculoService.register(veiculoRequest);
    }

    @Operation(summary = "Atualiza um veículo")
    @ApiResponse(responseCode = "200",
            description = "Veículo atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VeiculoResponse.class)))
    @PutMapping("/{veiculoId}")
    @ResponseStatus(HttpStatus.OK)
    public VeiculoResponse update(@PathVariable Long veiculoId, @RequestBody @Valid VeiculoRequest veiculoRequest) {
        return veiculoService.update(veiculoId, veiculoRequest);
    }

    @Operation(summary = "Deleta um veículo")
    @ApiResponse(responseCode = "204", description = "Veículo deletado com sucesso")
    @DeleteMapping("/{veiculoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long veiculoId) {
        veiculoService.delete(veiculoId);
    }

}
