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
                .cors(Customizer.withDefaults()) // <- Wichtig: erlaubt Verwendung deiner WebConfig-CORS
                .csrf(csrf -> csrf.disable()) // <- Verhindert CSRF-Schutz bei REST (wichtig für POST)
                .sessionManagement(sess -> sess.sessionCreationPolicy(STATELESS)) // <- Kein Session-Tracking nötig
                .authorizeHttpRequests(auth -> auth
                        // ❗ Preflight OPTIONS-Requests für alle Pfade explizit erlauben!
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()

                        // Swagger-UI & OpenAPI für alle freigeben
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Authentifizierung für eigene API
                        .requestMatchers("/api/**").authenticated()

                        // Rest blockieren
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())); // ← JWT-Verifizierung

        return http.build();
    }
}
