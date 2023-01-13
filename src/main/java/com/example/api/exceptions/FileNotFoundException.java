package com.example.api.exceptions;


import lombok.Getter;
import lombok.Setter;

public class FileNotFoundException extends Exception {

    @Getter
    @Setter
    private String qrString;

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
