package de.pls.stundenplaner.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**") // alle Endpoints
                        .allowedOrigins("http://localhost:3000") // dein Frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // alles erlauben
                        .allowedHeaders("*") // alle Header
                        .allowCredentials(true); // wenn Cookies/SessionID
            }
        };
    }
}
