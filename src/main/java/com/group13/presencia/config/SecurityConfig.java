package com.group13.presencia.config;

import com.group13.presencia.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Désactiver CSRF pour les API
                .authorizeHttpRequests(auth -> auth
                        // Autoriser l'accès aux endpoints d'authentification
                        .requestMatchers("/api/auth/**").permitAll()

                        // Protéger les endpoints des enseignants
                        .requestMatchers("/api/teachers/**").hasAuthority("TEACHER")

                        // Protéger les endpoints des étudiants
                        .requestMatchers("/api/students/**").hasAuthority("STUDENT")

                        // Protéger les endpoints des sessions
                        .requestMatchers("/api/sessions/**").hasAnyAuthority("TEACHER", "STUDENT")

                        // Protéger les endpoints des présences
                        .requestMatchers("/api/attendances/**").hasAnyAuthority("TEACHER", "STUDENT")

                        // Toutes les autres routes nécessitent une authentification
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/api/auth/login") // Endpoint de login
                        .successHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\": \"Connexion réussie\"}");
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(401);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Identifiants invalides\"}");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout") // Endpoint de déconnexion
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\": \"Déconnexion réussie\"}");
                        })
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}