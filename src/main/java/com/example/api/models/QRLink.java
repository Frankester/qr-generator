package com.example.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
@Entity
public class QRLink extends Persistence {

    private String url;

    private boolean isValid;

    public QRLink(){
        this.isValid = false;
    }

    public boolean verifyLink(){

        try {
            new URL(this.url).toURI();
            this.isValid = true;
        } catch (Exception e) {
            this.isValid = false;
        }

        return this.isValid;
    }
}
