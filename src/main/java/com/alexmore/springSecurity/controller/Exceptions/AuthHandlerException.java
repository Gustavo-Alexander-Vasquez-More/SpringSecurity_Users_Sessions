package com.alexmore.springSecurity.controller.Exceptions;

import com.alexmore.springSecurity.DTO.ErrorResponseDTO;
import com.alexmore.springSecurity.Exceptions.ErrorCredentialsException;
import com.alexmore.springSecurity.Exceptions.UserExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthHandlerException {

    @ExceptionHandler(ErrorCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleErrorCredentialsException(ErrorCredentialsException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(401, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
