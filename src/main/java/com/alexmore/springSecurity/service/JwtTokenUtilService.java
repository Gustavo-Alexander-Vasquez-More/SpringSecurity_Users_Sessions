package com.alexmore.springSecurity.service;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//Clase para generar y validar tokens JWT
@Service
public class JwtTokenUtilService {

    //Extraemos el Secret Key
    @Value("${jwt.secret}")
    private String secretKey;

    //Extraemos el tiempo de expiracion del token
    @Value("${jwt.expiration}")
    private Long expiration;


    public String generateToken(String username) {
        //Generamos el token JWT usando la libreria Jwts
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + expiration))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

}
