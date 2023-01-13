package com.example.api.controllers;

import com.example.api.exceptions.UserAlreadyExistsException;
import com.example.api.models.dto.LoginDTO;
import com.example.api.models.dto.RegisterDTO;
import com.example.api.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @Autowired
    AuthenticationService authService;

    @PostMapping(path = "/auth/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginDTO loginReq) {

        return ResponseEntity.ok(this.authService.login(loginReq));
    }


    @PostMapping(path = "/auth/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterDTO registerReq)
            throws UserAlreadyExistsException {

        this.authService.register(registerReq);

        return ResponseEntity.ok("User registered successfully");
    }

}
