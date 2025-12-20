package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SwaggerConfig { // Changed to capital 'S'

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info() // Added Info section
                        .title("Drug Interaction Checker API")
                        .version("1.0")
                        .description("API for checking medication interactions"))
                .servers(List.of(
                        new Server().url("https://9184.pro604cr.amypo.ai")
                ));
    }
}