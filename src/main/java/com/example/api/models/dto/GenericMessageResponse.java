package com.example.api.models.dto;

import lombok.Getter;
import lombok.Setter;

public class GenericMessageResponse {

    @Getter
    @Setter
    private String message;

    public GenericMessageResponse(String message){
        this.message = message;
    }
}
