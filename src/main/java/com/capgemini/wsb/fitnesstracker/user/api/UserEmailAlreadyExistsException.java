package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.exception.api.BusinessException;

/**
 * Exception indicating that the {@link User}' email already exists in DB.
 */
public class UserEmailAlreadyExistsException extends BusinessException {

    public UserEmailAlreadyExistsException(String email) {
        super("User with email=%s already exists".formatted(email));
    }
}
