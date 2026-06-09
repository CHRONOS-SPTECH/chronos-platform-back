package chronos.tech.infrastructure.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:8080") // Vite e Postman
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type", "OPTIONS")
                // Isso é crítico — permite que o frontend leia headers da resposta
                .exposedHeaders("Authorization")
                // Se for usar cookies com credenciais
                .allowCredentials(true); // true só se usar cookies
    }
}