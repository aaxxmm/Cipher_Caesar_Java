package ru.javarush.kozlov.caesarcipher.exception;

public class InvalidUserInputException extends RuntimeException {
    private String reason;

    public InvalidUserInputException(String reason) {
        this.reason = reason;
    }

    public InvalidUserInputException(String reason, Throwable cause) {
        super(reason, cause);
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }
}
