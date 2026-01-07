package de.pls.stundenplaner.util.exceptions.auth;

public class InvalidSessionException extends RuntimeException {

    public InvalidSessionException() {
        super("Invalid SessionID. Proceed to Login to get a SessionID.");
    }

}
