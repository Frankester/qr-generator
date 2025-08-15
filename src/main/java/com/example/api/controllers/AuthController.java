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


    private final AuthenticationService authService;

    @Autowired
    public AuthController(AuthenticationService authService){
        this.authService = authService;
    }


    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            content= @Content(examples = @ExampleObject(value = """
                    {
                      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmcmFua2VzdGVyMTIzNCIsImlhdCI6MTc1NTIwMTM1OSwiZXhwIjoxNzU1Mjg3NzU5fQ.b3K-Ajqsh8U46uCduHkEr7s7gMri_LUJ-0leoqpgn4w"
                    }
                    """), schema = @Schema(implementation = JwtResponse.class)) ),
    @ApiResponse(responseCode = "400",
            content = @Content(examples = @ExampleObject(
                    value = """
                            {
                              "error": "string"
                            }
                            """
            ), schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping(path = "/auth/login")
    public ResponseEntity<Object> loginUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = """
                            {
                              "username": "Frankester",
                              "password": "pass1234"
                            }
                            """)))
            @RequestBody LoginDTO loginReq) {

        return ResponseEntity.ok(this.authService.login(loginReq));
    }

    @ApiResponses(
            value =@ApiResponse(responseCode = "200",
            content = @Content(examples = @ExampleObject(value = """
                    {
                      "message": "User registered successfully"
                    }
                    """),schema = @Schema(implementation = GenericMessageResponse.class)))
    )
    @PostMapping(path = "/auth/register")
    public ResponseEntity<Object> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = """
                            {
                              "username": "Frankester",
                              "email": "francotomascallero@gmail.com",
                              "password": "pass1234"
                            }
                            """)))
            @RequestBody RegisterDTO registerReq)
            throws UserAlreadyExistsException {

        this.authService.register(registerReq);

        GenericMessageResponse messageResponse = new GenericMessageResponse("User registered successfully");
        return ResponseEntity.ok(messageResponse);
    }

}
