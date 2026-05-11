package chronos.tech.infrastructure.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI chronosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Chronos - Gestão Acadêmica")
                        .description("API para gerenciamento de fluxos acadêmicos e administrativos.")
                        .version("v0.0.1")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento - KairozTech")
                                .email("dev@kairoz.tech")));
    }
}