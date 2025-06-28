package com.studyflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // <- Important: allows usage of your WebConfig-CORS
                .csrf(csrf -> csrf.disable()) // <- Prevents CSRF protection for REST (important for POST)
                .sessionManagement(sess -> sess.sessionCreationPolicy(STATELESS)) // <- No session tracking needed
                .authorizeHttpRequests(auth -> auth
                        // ❗ Explicitly allow preflight OPTIONS requests for all paths!
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()

                        // Allow Swagger-UI & OpenAPI for everyone
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Authentication for own API
                        .requestMatchers("/api/**").authenticated()

                        // Block everything else
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())); // ← JWT verification

        return http.build();
    }
}
