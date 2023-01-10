package com.example.api.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class Persistence {

    @Id
    @GeneratedValue
    private Long id;
}
