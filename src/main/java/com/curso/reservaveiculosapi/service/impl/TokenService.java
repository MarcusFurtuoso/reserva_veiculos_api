package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.entity.UsuarioEntity;
import com.curso.reservaveiculosapi.service.ITokenService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService implements ITokenService {

    @Value("${config.jwt.secret}")
    private String secret;

    @Value("${config.jwt.expiration}")
    private String expiration;

    @Override
    public String generateToken(UsuarioEntity usuario) {
        try {
            Key secretKey = new SecretKeySpec(secret.getBytes(), "HMACSHA256");

            return Jwts.builder()
                .subject(usuario.getLogin())
                .issuer("RESERVA_VEICULOS_API")
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(secretKey)
                .compact();
        } catch (JwtException e) {
            throw new RuntimeException("Erro ao gerar token.");
        }
    }

    @Override
    public String validateToken(String token) {
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HMACSHA256");

        return Jwts.parser()
            .verifyWith(secretKey)
            .requireIssuer("RESERVA_VEICULOS_API")
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }
}
