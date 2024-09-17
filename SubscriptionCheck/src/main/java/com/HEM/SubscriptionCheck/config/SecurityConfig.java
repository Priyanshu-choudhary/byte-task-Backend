package com.HEM.SubscriptionCheck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Enable CORS
                .and()
                .csrf().disable() // Disable CSRF (optional, based on your requirements)
                .authorizeRequests()
                .requestMatchers("/github/**").permitAll() // Allow public access to all GitHub-related endpoints
                .anyRequest().authenticated() // Require authentication for any other endpoint
                .and()
                .oauth2Login().disable(); // Disable the default OAuth2 login page (optional)

        return http.build();
    }

    // Define CORS configuration globally
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173","https://byte-task-frontend.vercel.app") // Allow the frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow cookies if needed
            }
        };
    }
}
