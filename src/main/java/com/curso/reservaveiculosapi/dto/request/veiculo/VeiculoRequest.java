package com.curso.reservaveiculosapi.dto.request.veiculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record VeiculoRequest(
        @NotNull @NotBlank String nome,
        @NotNull @NotBlank String marca,
        @NotNull @NotBlank String tipo,
        @NotNull @NotBlank @Length(max = 300) String descricao,
        @NotNull BigDecimal preco
        ) {}
