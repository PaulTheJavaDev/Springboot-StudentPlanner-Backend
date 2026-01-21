package de.pls.stundenplaner.util.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
    public UnauthorizedException() {
        super("Not authorized to update this assignment");
    }
}
