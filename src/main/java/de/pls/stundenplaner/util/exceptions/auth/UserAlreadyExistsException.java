package de.pls.stundenplaner.util.exceptions.auth;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("User '" + username + "' already exists.");
    }

}
