package com.example.demo.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class OpenApiConfig {
    @Bean
    public OpenAPI openApi() {
        return  new OpenAPI()
                .info(new Info()
                        .title("APIRESTful")
                        .description("Documentacion de la APIREST de la aplicacion")
                        .version("v1.0")
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Tutorial de uso de la API").url("https://localhost:8070/docs"));
    }
}
