package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.dto.response.auth.UserAuthResponse;
import com.curso.reservaveiculosapi.entity.PerfilEntity;
import com.curso.reservaveiculosapi.entity.UsuarioEntity;
import com.curso.reservaveiculosapi.service.ITokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class TokenService implements ITokenService {

    @Value("${config.jwt.secret}")
    private String secret;

    @Value("${config.jwt.expiration}")
    private String expiration;

    @Override
    public String generateToken(UsuarioEntity usuario, UserAuthResponse user) {
        try {
            Key secretKey = new SecretKeySpec( secret.getBytes(), "HMACSHA256");

            return Jwts.builder()
                .subject(usuario.getLogin())
                .claim("user", user)
                .issuer("RESERVA_VEICULOS_API")
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(secretKey)
                .compact();
        } catch (JwtException e) {
            throw new RuntimeException("Erro ao gerar token." + e.getMessage());
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

    @Override
    public Long getUserIdFromJWT(String token) {

        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HMACSHA256");

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.parseLong(claims.getSubject());
    }
}
