package com.example.api.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends Persistence {

    private String username;
    private String email;
    private String password;
}
