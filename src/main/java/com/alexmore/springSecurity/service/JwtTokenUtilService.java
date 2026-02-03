package com.alexmore.springSecurity.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

//Clase para generar y validar tokens JWT
@Service
public class JwtTokenUtilService {

    // Esto genera una llave segura de 512 bits automáticamente al iniciar el servicio
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    //Extraemos el tiempo de expiracion del token
    @Value("${jwt.expiration}")
    private Long expiration;


    public String generateToken(String username) {
        //Generamos el token JWT usando la libreria Jwts
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

}
