package com.alexmore.springSecurity.security.config;

import com.alexmore.springSecurity.security.jwt.JwtRequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    //Configuración de seguridad HTTP, permitimos acceso público a /users/**
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults()) // Usa tu bean de CORS
                .authorizeHttpRequests(auth -> auth
                        // Asegúrate de que coincidan exactamente con tus @RequestMapping del Controller
                        .requestMatchers("/api/user/**", "/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                // DESACTIVA esto por completo para que no interfiera en el Login
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())

                // Mantén esto pero solo como respaldo, tu ControllerAdvice debería hacer el resto
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            // Si llegamos aquí, es porque intentaron entrar a una ruta protegida sin token
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"status\": 401, \"message\": \"No autorizado\"}");
                        })
                );

        return http.build();
    }

    //Configuración del PasswordEncoder, usamos BCrypt
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Configuración CORS básica
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            var cors = new org.springframework.web.cors.CorsConfiguration(); // Configuración CORS
            cors.setAllowedOrigins(java.util.List.of("*")); // Permitir todas las fuentes (en producción, especificar dominios)
            cors.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Permitir métodos HTTP comunes
            cors.setAllowedHeaders(java.util.List.of("*")); // Permitir todos los encabezados
            return cors;
        };
    }

    @Bean
    JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter();
    }
}