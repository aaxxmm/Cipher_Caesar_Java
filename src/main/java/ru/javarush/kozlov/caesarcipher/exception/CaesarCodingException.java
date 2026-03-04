package ru.javarush.kozlov.caesarcipher.exception;

public class CaesarCodingException extends RuntimeException {
    private String reason;

    public CaesarCodingException(String reason) {
        this.reason = reason;
    }

    public CaesarCodingException(String reason, Throwable cause) {
        super(reason, cause);
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }
}
