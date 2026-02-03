package com.alexmore.springSecurity.Exceptions;

import org.springframework.security.core.AuthenticationException;

public class ErrorCredentialsException extends AuthenticationException {
    public ErrorCredentialsException(String message) {
        super(message);
    }
}
