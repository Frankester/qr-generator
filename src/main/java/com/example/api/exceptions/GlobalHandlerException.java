package com.example.api.exceptions;

import com.example.api.models.dto.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ControllerAdvice
public class GlobalHandlerException {

    private ErrorResponse generateErrorResponse (Exception ex){
        return new ErrorResponse(ex.getLocalizedMessage());
    }

    @ExceptionHandler({InvalidLinkException.class, UserAlreadyExistsException.class, InvalidQRPixelSizeException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleBadRequestExceptions(Exception ex){
        return this.generateErrorResponse(ex);
    }


    @ExceptionHandler(FileNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse fileNotFound(FileNotFoundException ex){
        return this.generateErrorResponse(ex);
    }


    @ExceptionHandler(AccesDeniedResourceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse accesDeniedResource(AccesDeniedResourceException ex){
        return this.generateErrorResponse(ex);
    }


    @ExceptionHandler({ AuthenticationException.class, InsufficientAuthenticationException.class })
    @ResponseBody
    public ResponseEntity<Object> handleAuthenticationException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(this.generateErrorResponse(ex));
    }

    @ExceptionHandler(DirectoryCreationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleDirectoryCreationException(DirectoryCreationException ex){
        return this.generateErrorResponse(ex);
    }
}
