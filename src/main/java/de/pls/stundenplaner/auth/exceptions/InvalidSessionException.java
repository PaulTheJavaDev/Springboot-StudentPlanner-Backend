package de.pls.stundenplaner.auth.exceptions;

public class InvalidSessionException extends RuntimeException {

    public InvalidSessionException() {
        super("Invalid SessionID.");
    }

}
