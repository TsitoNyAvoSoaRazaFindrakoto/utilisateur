package perso.utilisateur.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestion des Utilisateurs")
                        .version("1.0")
                        .description("Documentation de l'API REST pour gérer les utilisateurs.")
                        .contact(new Contact()
                                .name("Support Technique")
                                .email("support@exemple.com")));
    }
}

