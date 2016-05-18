package edu.pitt.dbmi.ccd.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
public class CrossOriginRequestFilter {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedMethods("GET", "POST")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true);
            }
        };
    }
}
