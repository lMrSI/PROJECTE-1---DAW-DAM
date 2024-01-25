package com.example.demo.config;

import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.hateoas.Links;

import java.util.Arrays;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openApi() {
        SpringDocUtils.getConfig().addResponseTypeToIgnore(Links.class);
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                //Bloquea todos los end-points con autentificación bearer
                .addSecurityItem(
                    new SecurityRequirement()
                    .addList(securitySchemeName, Arrays.asList("read", "write"))
                )
                .components(
                    new Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        )
                )
                //Modifica la información por defecto
                .info(new Info()
                    .title("APIREST - BOLSA DE TRABAJO ITICBCN").version("v1.0")
                    .description("Esta es una documentación de la APIREST que permite visualzar con ejemplos las funcionalidades que ofrece este proyecto")
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación en formato JSON").url("https://localhost:8070/docs"));
    }
}