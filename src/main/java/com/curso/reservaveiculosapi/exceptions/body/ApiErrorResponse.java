package com.curso.reservaveiculosapi.exceptions.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Locale;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public sealed class ApiErrorResponse implements Serializable permits ValidateExceptionResponse {

    protected  Integer status;
    protected  String error;
    protected  String message;
    protected  String path;
    protected  Locale locale;
    protected OffsetDateTime timestamp;

}