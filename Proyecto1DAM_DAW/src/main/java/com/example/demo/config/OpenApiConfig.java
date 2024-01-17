package com.example.demo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
