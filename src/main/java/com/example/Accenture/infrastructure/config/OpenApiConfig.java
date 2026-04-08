package com.example.Accenture.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI franchiseOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Franchise API")
                .description("Reactive API for managing franchises, branches and products")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Fabian Mauricio Guerra Bedoya")
                    .email("maurogebe.96@gmail.com"))
                .license(new License()
                    .name("MIT")))
            .externalDocs(new ExternalDocumentation()
                .description("Project README")
                .url("https://github.com/maurogebe/test-accenture"));
    }
}