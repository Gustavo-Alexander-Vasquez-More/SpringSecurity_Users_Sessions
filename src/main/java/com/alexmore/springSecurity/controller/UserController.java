package com.alexmore.springSecurity.controller;

import com.alexmore.springSecurity.DTO.UserCreatedResponseDTO;
import com.alexmore.springSecurity.DTO.UserRequestDTO;
import com.alexmore.springSecurity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/user")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUserByUsername() {
        return "Hola mundo";
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<UserCreatedResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {

        UserCreatedResponseDTO createdUser = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(createdUser);
    }
}
