package com.example.api.models.dto;

import lombok.Getter;
import lombok.Setter;

public class ErrorResponse {

    @Getter
    @Setter
    private String error;

    public ErrorResponse(String errorMessage){
        this.error = errorMessage;
    }
}
