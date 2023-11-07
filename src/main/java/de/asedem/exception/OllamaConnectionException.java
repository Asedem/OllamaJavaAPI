package de.asedem.exception;

public class OllamaConnectionException extends Exception {

    private final Exception baseException;

    public OllamaConnectionException(Exception baseException) {
        this.baseException = baseException;
    }

    public Exception getBaseException() {
        return baseException;
    }
}
