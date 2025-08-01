package com.example.api.exceptions;

import com.example.api.models.QRLink;

public class InvalidQRPixelSizeException extends Exception{

    public InvalidQRPixelSizeException(String message) {
        super(message);
    }
}
