package de.pls.stundenplaner.util.exceptions;

public class FailureToCreateEntityException extends RuntimeException {
    public FailureToCreateEntityException(String message) {
        super(message);
    }
    public FailureToCreateEntityException() {}
}
