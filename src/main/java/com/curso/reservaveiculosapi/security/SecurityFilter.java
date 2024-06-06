package com.curso.reservaveiculosapi.security;

import com.curso.reservaveiculosapi.exceptions.custom.ApiAuthException;
import com.curso.reservaveiculosapi.repository.UsuarioRepository;
import com.curso.reservaveiculosapi.security.exception.CustomAuthException;
import com.curso.reservaveiculosapi.service.impl.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final CustomAuthException customAuthException;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            var token = this.recoverToken(request);

            if (token != null) {
                var login = tokenService.validateToken(token);
                UserDetails user = usuarioRepository.findByLogin(login);

                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (MalformedJwtException malformedJwtException) {
            ApiAuthException apiAuthException = new ApiAuthException("Invalid JWT token - " + malformedJwtException.getClass().getSimpleName());
            this.customAuthException.commence(request, response, apiAuthException);
            return;
        } catch (SignatureException signatureException) {
            ApiAuthException apiAuthException = new ApiAuthException("Invalid JWT signature - " + signatureException.getClass().getSimpleName());
            this.customAuthException.commence(request, response, apiAuthException);
            return;
        } catch (ExpiredJwtException expiredJwtException) {
            ApiAuthException apiAuthException = new ApiAuthException("Expired JWT token - " + expiredJwtException.getClass().getSimpleName());
            this.customAuthException.commence(request, response, apiAuthException);
            return;
        } catch(UnsupportedJwtException unsupportedJwtException) {
            ApiAuthException apiAuthException = new ApiAuthException("Unsupported JWT token - " + unsupportedJwtException.getClass().getSimpleName());
            this.customAuthException.commence(request, response, apiAuthException);
            return;
        } catch (IllegalArgumentException illegalArgumentException) {
            ApiAuthException apiAuthException = new ApiAuthException("JWT claims string is empty - " + illegalArgumentException.getClass().getSimpleName());
            this.customAuthException.commence(request, response, apiAuthException);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
