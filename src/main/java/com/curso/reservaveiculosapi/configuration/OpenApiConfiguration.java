package com.curso.reservaveiculosapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Reserva de Veículo API")
                        .version("1.0")
                        .description("Documentação da API de reserva de veículos.")
                        .license(
                                new License()
                                        .name("MIT License")
                                        .url("https://opensource.org/licenses/MIT")
                        )
                );
    }
}
