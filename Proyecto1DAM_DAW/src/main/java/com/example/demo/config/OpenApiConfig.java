package com.example.demo.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
//import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;


@Configuration
/*@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")

 */
public class OpenApiConfig {
    /*private final String moduleName;
    private final String apiVersion;
    public OpenApiConfig(
            @Value("${module-name}") String moduleName,
            @Value("${api-version}") String apiVersion) {
        this.moduleName = moduleName;
        this.apiVersion = apiVersion;
    }*/
    @Bean
    public OpenAPI openApi() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName, Arrays.asList("read", "write"))
                )
                .components(
                    new Components()
                        .addSecuritySchemes(securitySchemeName,
                            new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                //.in(SecurityScheme.In.HEADER).name("Authorization")
                        )
                )
                .info(new Info()
                        .title("APIREST DE BORSA DE TREBALL")
                        .description("Documentacion de la APIREST de la aplicacion")
                        .version("v1.0")
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Tutorial de uso de la API").url("https://localhost:8070/docs"));

    }
}