package com.alexmore.springSecurity.controller;

import com.alexmore.springSecurity.DTO.LoginRequest;
import com.alexmore.springSecurity.DTO.TokenResponseDTO;
import com.alexmore.springSecurity.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    public final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //Endpoint para el logueo de usuarios
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequest loginRequest) {
        TokenResponseDTO tokenResponseDTO = authService.login(loginRequest);
        return ResponseEntity.ok(tokenResponseDTO);
    }
}
