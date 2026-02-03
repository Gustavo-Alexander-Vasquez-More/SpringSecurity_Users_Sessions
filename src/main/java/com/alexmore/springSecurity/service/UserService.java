package com.alexmore.springSecurity.service;

import com.alexmore.springSecurity.DTO.LoginRequest;
import com.alexmore.springSecurity.DTO.UserCreatedResponseDTO;
import com.alexmore.springSecurity.DTO.UserLoginDetailDTO;
import com.alexmore.springSecurity.DTO.UserRequestDTO;
import com.alexmore.springSecurity.Exceptions.ErrorCredentialsException;
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

    //Servicio para obtener un usuario por su nombre de usuario
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    //Servicio para extraer un usuario por su username
    public UserLoginDetailDTO getUserLoginDetailByUsername(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            return new UserLoginDetailDTO(user.getUsername(), user.getPassword());
        }
        return null;
    }

    //Servicio para verificar si un usuario existe por su nombre de usuario
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    //Servicio para verificar si la contraseña de un usuario es correcta usando Bcrypt
    public boolean isPasswordCorrect(String rawPassword, String hashedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, hashedPassword);
    }

    //Servicio para verificar las credenciales de un usuario
    public void verifyCredentials(LoginRequest loginRequest) {
        //usamos el metodo de getUserLoginDetailByUsername para verificar si el usuario existe
        UserLoginDetailDTO userLoginDetailDTO = getUserLoginDetailByUsername(loginRequest.getUsername());
        if (userLoginDetailDTO == null) {
            throw new ErrorCredentialsException("Invalid username or password.");
        }
        //usamos el metodo isPasswordCorrect para verificar la contraseña
        boolean isPasswordCorrect= isPasswordCorrect(loginRequest.getPassword(), userLoginDetailDTO.getPassword());
        if(!isPasswordCorrect){
            throw new ErrorCredentialsException("Invalid username or password.");
        }
    }

    //Servicio para crear un usuario con contraseña hasheada
    public UserCreatedResponseDTO createUser(UserRequestDTO userRequestDTO) {
        //Primero verificamos que no exista el usuario
        boolean userExists= userExists(userRequestDTO.getUsername());
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
