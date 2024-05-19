package com.curso.reservaveiculosapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class HttpSecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        //  Auth //
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        //  Usuário //
                        .requestMatchers(HttpMethod.GET, "/api/usuario").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuario/veiculos").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/usuario/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuario/login").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/usuario/add-perfil").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/usuario/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/usuario/**").hasRole("ADMIN")
                        //  Perfil //
                        .requestMatchers(HttpMethod.GET, "/api/perfil").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/perfil/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/perfil").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/perfil/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/perfil/**").hasRole("ADMIN")
                        //  Reserva //
                        .requestMatchers(HttpMethod.GET, "/api/reserva").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/reserva/usuario").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/reserva").authenticated()
                        //  Veículo //
                        .requestMatchers(HttpMethod.GET, "/api/veiculo").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/veiculo/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/veiculo").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/veiculo/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/veiculo/**").authenticated()
                        // Imagem //
                        .requestMatchers(HttpMethod.POST, "/api/imagem-veiculo/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/imagem-veiculo/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/imagem-veiculo/**").authenticated()
                        //  Swagger //
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
