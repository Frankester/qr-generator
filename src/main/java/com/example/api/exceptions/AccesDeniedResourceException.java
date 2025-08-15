package com.example.api.exceptions;


public class AccesDeniedResourceException extends Exception {


    public AccesDeniedResourceException(String message) {
        super(message);
    }

    public AccesDeniedResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccesDeniedResourceException(Throwable cause) {
        super(cause);
    }

    public AccesDeniedResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
