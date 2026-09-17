package chronos.tech.infrastructure.configs;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        // Usando a classe padrão do Jakarta Servlet diretamente, sem erros de importação
        jakarta.servlet.MultipartConfigElement config = new jakarta.servlet.MultipartConfigElement(
                "",
                50L * 1024L * 1024L, // Max File Size (50MB)
                50L * 1024L * 1024L, // Max Request Size (50MB)
                0                    // File Size Threshold
        );
        return config;
    }
}