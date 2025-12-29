package de.pls.stundenplaner.util.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("User '" + username + "' already exists.");
    }

}
