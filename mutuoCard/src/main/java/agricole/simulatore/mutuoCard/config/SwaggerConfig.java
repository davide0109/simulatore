package agricole.simulatore.mutuoCard.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Documentation")
                .version("1.0.0")
                .description("API documentation for the project"))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi gestioneFileApi() {
        return GroupedOpenApi.builder()
            .group("Gestione File")
            .pathsToMatch("/csv/**", "/pdf/**")
            .build();
    }

    @Bean
    public GroupedOpenApi outputApi() {
        return GroupedOpenApi.builder()
            .group("Gestione Configurazione")
            .pathsToMatch("/gestione-listino/**")
            .build();
    }

    @Bean
    public GroupedOpenApi simulazioneConfigurazioneApi() {
        return GroupedOpenApi.builder()
            .group("Gestione Simulazione")
            .pathsToMatch("/output/**", "/gestione-simulazione/**")
            .build();
    }
}