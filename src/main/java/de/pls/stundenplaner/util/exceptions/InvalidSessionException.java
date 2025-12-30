package de.pls.stundenplaner.util.exceptions;

public class InvalidSessionException extends RuntimeException {

    public InvalidSessionException() {
        super("Invalid SessionID.");
    }

}
