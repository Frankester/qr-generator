package com.example.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class QRLink extends Persistence {

    private String url;

    private boolean isValid;

    public QRLink(){
        this.isValid = false;
    }

    public void verifyLink(){
        //TODO verify this.url link
    }
}
