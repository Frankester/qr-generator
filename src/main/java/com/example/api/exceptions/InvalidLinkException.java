package com.example.api.exceptions;

import com.example.api.models.QRLink;
import lombok.Getter;
import lombok.Setter;

public class InvalidLinkException extends Exception{

    @Getter
    @Setter
    private QRLink link;

    public InvalidLinkException(String message, QRLink link) {
        super(message);
        this.link = link;
    }

    public InvalidLinkException(String message, Throwable cause, QRLink link) {
        super(message, cause);
        this.link = link;
    }

    public InvalidLinkException(Throwable cause, QRLink link) {
        super(cause);
        this.link = link;
    }

    public InvalidLinkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, QRLink link) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.link = link;
    }
}
