package ru.javarush.kozlov.caesarcipher.exception;

public class FileProcessingException extends RuntimeException {
    String reason;

    public FileProcessingException(String reason) {
        this.reason = reason;
    }

    public FileProcessingException(String reason, Throwable cause) {
        super(reason, cause);
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }

} //class FileProcessingException

