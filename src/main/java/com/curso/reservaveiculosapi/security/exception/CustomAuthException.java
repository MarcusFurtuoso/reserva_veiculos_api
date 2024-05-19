package com.curso.reservaveiculosapi.security.exception;

import com.curso.reservaveiculosapi.exceptions.body.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.time.OffsetDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthException implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Error: ", authException);

        // Create ApiErrorResponse
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(authException.getClass().getAnnotation(ResponseStatus.class).value().value())
                .error(authException.getClass().getSimpleName())
                .message(authException.getMessage())
                .path(request.getRequestURI())
                .locale(request.getLocale())
                .timestamp(OffsetDateTime.now())
                .build();

        // Set response status and content type
        response.setStatus(authException.getClass().getAnnotation(ResponseStatus.class).value().value());
        response.setContentType("application/json");

        // Write ApiErrorResponse to response
        response.getWriter().write(objectMapper.writeValueAsString(apiErrorResponse));
    }
}
