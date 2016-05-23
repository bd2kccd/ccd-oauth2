package edu.pitt.dbmi.ccd.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;

/**
 * OAuth 2.0 package for CCD applications
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@SpringBootApplication
@Import({CCDDatabaseApplication.class})
@EnableResourceServer
// @EnableWebMvc
public class CCDSecurityApplication {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(CCDSecurityApplication.class, args);
    }

   // @Bean
   // public WebMvcConfigurer corsConfigurer() {
   //     return new WebMvcConfigurerAdapter() {
   //         @Override
   //         public void addCorsMappings(CorsRegistry registry) {
   //             registry.addMapping("/**").allowedOrigins("http://localhost:8080");
   //         }
   //     };
   // }

   // @Bean
   // public FilterRegistrationBean corsFilter() {
   //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
   //     CorsConfiguration config = new CorsConfiguration();
   //     config.setAllowCredentials(true);
   //     config.addAllowedOrigin("*");
   //     config.addAllowedHeader("*");
   //     config.addAllowedMethod("OPTIONS");
   //     config.addAllowedMethod("HEAD");
   //     config.addAllowedMethod("GET");
   //     config.addAllowedMethod("PUT");
   //     config.addAllowedMethod("POST");
   //     config.addAllowedMethod("DELETE");
   //     config.addAllowedMethod("PATCH");
   //     source.registerCorsConfiguration("/**", config);
   //     // return new CorsFilter(source);
   //     final FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
   //     bean.setOrder(0);
   //     return bean;
   // }

}
