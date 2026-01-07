package de.pls.stundenplaner.util.exceptions.auth;

public class InvalidLoginException extends RuntimeException {

    public InvalidLoginException() {
        super("Invalid username or password.");
    }

}
