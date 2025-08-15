package com.example.api.exceptions;


import lombok.Getter;

public class FileNotFoundException extends Exception {

    @Getter
    private final String qrString;

    public FileNotFoundException(String message, String qrString) {
        super(message);
        this.qrString = qrString;
    }

    public FileNotFoundException(String message, Throwable cause, String qrString) {
        super(message, cause);
        this.qrString = qrString;
    }

    public FileNotFoundException(Throwable cause, String qrString) {
        super(cause);
        this.qrString = qrString;
    }

    public FileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String qrString) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.qrString = qrString;
    }
}
