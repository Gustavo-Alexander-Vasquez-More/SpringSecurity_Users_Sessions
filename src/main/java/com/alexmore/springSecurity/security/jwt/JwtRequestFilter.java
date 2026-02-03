package com.alexmore.springSecurity.security.jwt;

import com.alexmore.springSecurity.service.JwtTokenUtilService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtilService jwtTokenUtilService; // Servicio para manejar tokens JWT

    @Autowired
    private UserDetailsService userDetailsService; // Servicio para cargar detalles de usuario


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 1. Verificar si viene el token en el header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtTokenUtilService.extractUsername(jwt);
            } catch (Exception e) {
                logger.error("Invalid JWT Token", e);
            }
        }

        // 2. Si hay usuario y no está autenticado en el contexto actual
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 3. Validar el token
            if (jwtTokenUtilService.validateToken(jwt, userDetails.getUsername())) {

                // IMPORTANTE: Aquí pasamos los ROLES (authorities) a Spring
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 4. Inyectar el usuario en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 5. Continuar con la petición
        chain.doFilter(request, response);
    }
}
