package com.example.api.exceptions;


public class InvalidLinkException extends Exception{


    public InvalidLinkException(String message) {
        super(message);
    }

    public InvalidLinkException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLinkException(Throwable cause) {
        super(cause);
    }

    public InvalidLinkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
