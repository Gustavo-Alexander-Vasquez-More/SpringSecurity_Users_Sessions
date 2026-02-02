package com.alexmore.springSecurity.Exceptions;

import jakarta.persistence.EntityExistsException;

public class UserExistException extends EntityExistsException {
    public UserExistException(String message) {
        super(message);
    }
}
