package com.curso.reservaveiculosapi.exceptions.body;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public non-sealed class ValidateExceptionResponse extends ApiErrorResponse {

    private List<Field> fields;

    @Data
    @Builder
    public static class Field {
        public String fieldName;
        public String fieldErrorMessage;
    }
}

