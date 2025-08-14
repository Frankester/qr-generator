package com.example.api.controllers;

import com.example.api.exceptions.UserAlreadyExistsException;
import com.example.api.models.dto.*;
import com.example.api.services.AuthenticationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @Autowired
    AuthenticationService authService;


    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            content= @Content(examples = @ExampleObject(value = "{\n" +
                    "  \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmcmFua2VzdGVyMTIzNCIsImlhdCI6MTc1NTIwMTM1OSwiZXhwIjoxNzU1Mjg3NzU5fQ.b3K-Ajqsh8U46uCduHkEr7s7gMri_LUJ-0leoqpgn4w\"\n" +
                    "}"), schema = @Schema(implementation = JwtResponse.class)) ),
    @ApiResponse(responseCode = "400",
            content = @Content(examples = @ExampleObject(
                    value = "{\n" +
                            "  \"error\": \"string\"\n" +
                            "}"
            ), schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping(path = "/auth/login")
    public ResponseEntity<Object> loginUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = "{\n" +
                    "  \"username\": \"Frankester\",\n" +
                    "  \"password\": \"pass1234\"\n" +
                    "}")))
            @RequestBody LoginDTO loginReq) {

        return ResponseEntity.ok(this.authService.login(loginReq));
    }

    @ApiResponses(
            value =@ApiResponse(responseCode = "200",
            content = @Content(examples = @ExampleObject(value = "{\n" +
                    "  \"message\": \"User registered successfully\"\n" +
                    "}"),schema = @Schema(implementation = GenericMessageResponse.class)))
    )
    @PostMapping(path = "/auth/register")
    public ResponseEntity<Object> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = "{\n" +
                            "  \"username\": \"Frankester\",\n" +
                            "  \"email\": \"francotomascallero@gmail.com\",\n" +
                            "  \"password\": \"pass1234\"\n" +
                            "}")))
            @RequestBody RegisterDTO registerReq)
            throws UserAlreadyExistsException {

        this.authService.register(registerReq);

        GenericMessageResponse messageResponse = new GenericMessageResponse("User registered successfully");
        return ResponseEntity.ok(messageResponse);
    }

}
