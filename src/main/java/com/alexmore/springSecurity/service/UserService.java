package com.alexmore.springSecurity.service;

import com.alexmore.springSecurity.DTO.UserCreatedResponseDTO;
import com.alexmore.springSecurity.DTO.UserRequestDTO;
import com.alexmore.springSecurity.Exceptions.UserExistException;
import com.alexmore.springSecurity.model.User;
import com.alexmore.springSecurity.repository.UserRepository;
import com.alexmore.springSecurity.utils.RoleUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //Servicio para hashear contraseña del usuario
    public String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    //Servicio para crear un usuario con contraseña hasheada
    public UserCreatedResponseDTO createUser(UserRequestDTO userRequestDTO) {
        //Primero verificamos que no exista el usuario
        boolean userExists=userRepository.existsByUsername(userRequestDTO.getUsername());
        if(userExists){
            throw new UserExistException("The username '"+userRequestDTO.getUsername()+"' is already taken.");
        }
        String hashedPassword = hashPassword(userRequestDTO.getPassword()); // Hashear la contraseña antes de guardarla
        //Creamos el usuario y lo guardamos en la base de datos
        User user = new User(
                userRequestDTO.getUsername(),
                hashedPassword,
                userRequestDTO.getEmail(),
                RoleUser.valueOf(userRequestDTO.getRole().toUpperCase()));
        userRepository.save(user);

        //Devolvemos el DTO de respuesta sin la contraseña
        return new UserCreatedResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole().toString() ,user.getCreatedAt());
    }
}
