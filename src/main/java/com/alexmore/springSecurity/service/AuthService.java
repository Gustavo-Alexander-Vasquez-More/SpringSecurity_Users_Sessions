package com.alexmore.springSecurity.service;

import com.alexmore.springSecurity.DTO.LoginRequest;
import com.alexmore.springSecurity.DTO.TokenResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public final JwtTokenUtilService jwtTokenUtilService; // Inyección de dependencia del JwtTokenUtil
    public final UserService userService;

    public AuthService(JwtTokenUtilService jwtTokenUtilService, UserService userService) {
        this.jwtTokenUtilService = jwtTokenUtilService;
        this.userService = userService;
    }

    //Servicio para el logueo de usuarios
    public TokenResponseDTO login(LoginRequest loginRequest){
        //Primero verificamos que las credenciales de usuario sean correctas
        //1. Verificar que el usuario existe en la base de datos
        userService.verifyCredentials(loginRequest);
        //3. Si las credenciales son correctas, generamos el token JWT
        String token = jwtTokenUtilService.generateToken(loginRequest.getUsername());
        return new TokenResponseDTO(token);
    }
}
