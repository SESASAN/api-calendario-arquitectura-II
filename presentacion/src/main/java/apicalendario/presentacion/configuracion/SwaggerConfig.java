package apicalendario.presentacion.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    private static final String ESQUEMA = "bearerAuth";

    @Bean
    public OpenAPI documentoOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Calendario")
                        .version("1.0.0")
                        .description("Calendario laboral (Spring Boot + PostgreSQL, arquitectura Onion). "
                                + "Cliente de la API Festivos. Obtenga el token en /api/usuarios/validar."))
                .addSecurityItem(new SecurityRequirement().addList(ESQUEMA))
                .components(new Components().addSecuritySchemes(ESQUEMA, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}
