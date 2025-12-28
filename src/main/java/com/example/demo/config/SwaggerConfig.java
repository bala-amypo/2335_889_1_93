// package com.example.demo.config;

// import io.swagger.v3.oas.models.*;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.security.*;
// import io.swagger.v3.oas.models.servers.Server;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import java.util.List;

// @Configuration
// public class SwaggerConfig {

//     @Bean
//     public OpenAPI openAPI() {

//         SecurityScheme jwtScheme = new SecurityScheme()
//                 .type(SecurityScheme.Type.HTTP)
//                 .scheme("bearer")
//                 .bearerFormat("JWT")
//                 .in(SecurityScheme.In.HEADER)
//                 .name("Authorization");

//         SecurityRequirement securityRequirement =
//                 new SecurityRequirement().addList("bearerAuth");

//         Server customServer = new Server()
//                 .url("https://9313.pro604cr.amypo.ai/")
//                 .description("Custom deployment server");

//         return new OpenAPI()
//                 .info(new Info()
//                         .title("Drug Interaction Checker API")
//                         .description("API for checking drug interactions using active ingredients")
//                         .version("1.0.0")
//                 )
//                 .servers(List.of(customServer))
//                 .components(new Components()
//                         .addSecuritySchemes("bearerAuth", jwtScheme)
//                 )
//                 .addSecurityItem(securityRequirement);
//     }
// }

package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    public static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                // ✅ Server configuration (you already had this)
                .servers(List.of(
                        new Server().url("https://9313.pro604cr.amypo.ai/")
                ))

                // ✅ THIS enables the Authorize button
                .addSecurityItem(
                        new SecurityRequirement().addList(SECURITY_SCHEME_NAME)
                )

                // ✅ JWT Bearer definition
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        SECURITY_SCHEME_NAME,
                                        new SecurityScheme()
                                                .name("Authorization")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
