package com.alexmore.springSecurity.security.jwt;

import com.alexmore.springSecurity.DTO.LoginRequest;
import com.alexmore.springSecurity.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Optional;

//Clase para generar y validar tokens JWT
@Service
public class JwtTokenUtil {

    //obtenemos la duracion del token desde application.properties que esta como variable de entorno
    private final Long jwtExpirationInMs = Long.parseLong(System.getenv("JWT_EXPIRATION"));
    //obtenemos la clave secreta desde application.properties que esta como variable de entorno
    private final String jwtSecret = System.getenv("JWT_SECRET");

    //Generacion del token con JWT
    @Bean
    public String generateToken(String username) {
        // Lógica para generar el token JWT usando el nombre de usuario usando la librería JWT

        return "token_jwt_generado_para_" + username; // Reemplaza esto con el token real generado

    }


}
