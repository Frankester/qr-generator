package com.example.api.exceptions;

import com.example.api.models.User;
import lombok.Getter;
import lombok.Setter;

public class AccesDeniedResourceException extends Exception {

    @Getter
    @Setter
    private User user;

    public AccesDeniedResourceException(String message, User user) {
        super(message);
        this.user = user;
    }

    public AccesDeniedResourceException(String message, Throwable cause, User user) {
        super(message, cause);
        this.user = user;
    }

    public AccesDeniedResourceException(Throwable cause, User user) {
        super(cause);
        this.user = user;
    }

    public AccesDeniedResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, User user) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.user = user;
    }
}
