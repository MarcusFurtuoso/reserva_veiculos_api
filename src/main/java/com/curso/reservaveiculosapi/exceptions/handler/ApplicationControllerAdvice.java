package com.curso.reservaveiculosapi.exceptions.handler;

import com.curso.reservaveiculosapi.exceptions.body.ApiErrorResponse;
import com.curso.reservaveiculosapi.exceptions.body.ValidateExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class ApplicationControllerAdvice {

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ApiErrorResponse> handleArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request, Locale locale) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        var fieldList = processFieldError(fieldErrors);

        var httpStatus = HttpStatus.BAD_REQUEST;

        ApiErrorResponse apiErrorResponse = ValidateExceptionResponse.builder()
                .status(httpStatus.value())
                .error(ex.getClass().getSimpleName())
                .message("one or more fields are invalid")
                .path(request.getRequestURI())
                .locale(locale)
                .timestamp(OffsetDateTime.now())
                .fields(fieldList)
                .build();

        return ResponseEntity.status(httpStatus).body(apiErrorResponse);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    protected ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request, Locale locale) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        var apiErrorBody = this.toApiErrorBody(httpStatus, ex, request, locale);
        return ResponseEntity.status(httpStatus).body(apiErrorBody);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request, Locale locale) {
        HttpStatus httpStatus = this.getStatusOfExceptionClass(ex);
        var apiErrorBody = this.toApiErrorBody(httpStatus, ex, request, locale);
        return ResponseEntity.status(httpStatus).body(apiErrorBody);
    }

    @ExceptionHandler({ MissingServletRequestParameterException.class })
    public ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request, Locale locale) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        var apiErrorBody = this.toApiErrorBody(httpStatus, ex, request, locale);
        return ResponseEntity.status(httpStatus).body(apiErrorBody);
    }

    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request, Locale locale) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        var apiErrorBody = this.toApiErrorBody(httpStatus, ex, request, locale);
        return ResponseEntity.status(httpStatus).body(apiErrorBody);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex, HttpServletRequest request, Locale locale) {
        HttpStatus httpStatus = this.getStatusOfExceptionClass(ex);
        var apiErrorBody = this.toApiErrorBody(httpStatus, ex, request, locale);
        return ResponseEntity.status(httpStatus).body(apiErrorBody);
    }

    private ApiErrorResponse toApiErrorBody(HttpStatus httpStatus, Throwable ex, HttpServletRequest request, Locale locale) {
        return ApiErrorResponse.builder()
                .status(httpStatus.value())
                .error(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .locale(locale)
                .timestamp(OffsetDateTime.now())
                .build();
    }

    private List<ValidateExceptionResponse.Field> processFieldError(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> ValidateExceptionResponse.Field.builder()
                        .fieldName(fieldError.getField())
                        .fieldErrorMessage(fieldError.getDefaultMessage())
                        .build()
                ).toList();
    }

    private HttpStatus getStatusOfExceptionClass(Throwable throwable) {
        return throwable.getClass().getDeclaredAnnotation(ResponseStatus.class).value();
    }

}
