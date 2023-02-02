package com.example.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(InvalidLinkException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String keySiteInUse(InvalidLinkException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String fileNotFound(FileNotFoundException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userAlreadyExists(UserAlreadyExistsException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(AccesDeniedResourceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String userAlreadyExists(AccesDeniedResourceException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<Object> handleAuthenticationException(Exception ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getLocalizedMessage());
    }
}
