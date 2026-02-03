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

    //Extraemos la clave secreta del application.properties
    @Value("${jwt.secret}")
    private String secret;

    //Extraemos el tiempo de expiracion del token
    @Value("${jwt.expiration}")
    private Long expiration;

    //extractUsername
    //Servicio para extraer el nombre de usuario del token JWT
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //isTokenExpired
    //Servicio para verificar si el token JWT ha expirado
    public boolean isTokenExpired(String token) {
        java.util.Date expirationDate = Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new java.util.Date());
    }

    //validateToken
    //Servicio para validar el token JWT
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String generateToken(String username) {
        //Generamos el token JWT usando la libreria Jwts
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + expiration))
                .signWith( Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

}
