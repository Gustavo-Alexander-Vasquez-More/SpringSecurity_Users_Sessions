package com.alexmore.springSecurity.service;

import com.alexmore.springSecurity.security.jwt.JwtTokenUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
   public final JwtTokenUtil jwtTokenUtil; // Inyección de dependencia del JwtTokenUtil

    public AuthService(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }
}
