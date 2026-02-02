package com.alexmore.springSecurity.controller.Exceptions;

import com.alexmore.springSecurity.DTO.ErrorResponseDTO;
import com.alexmore.springSecurity.Exceptions.UserExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandlerController {

    // Maneja la excepción UserExistException y devuelve una respuesta adecuada
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserExistsException(UserExistException ex) {
        // Creamos el DTO con la información del error
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );

        // Retornamos el DTO dentro del ResponseEntity con el status 409
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}
