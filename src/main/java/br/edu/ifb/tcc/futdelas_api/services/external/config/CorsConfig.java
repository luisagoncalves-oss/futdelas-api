package br.edu.ifb.tcc.futdelas_api.services.external.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private static final String[] ALLOWED_ORIGIN_PATTERNS = {
        "http://localhost:*",
        "https://*.onrender.com", 
        "https://*.com.br"
    };
    
    private static final String[] ALLOWED_METHODS = {
        "GET", "OPTIONS"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns(ALLOWED_ORIGIN_PATTERNS)
                .allowedMethods(ALLOWED_METHODS)
                .allowedHeaders("*");
    }
}