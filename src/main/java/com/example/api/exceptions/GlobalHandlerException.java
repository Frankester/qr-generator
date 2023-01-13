package com.example.api.exceptions;

import org.springframework.http.HttpStatus;
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
    String keySiteInUse(FileNotFoundException ex){
        return ex.getLocalizedMessage();
    }

}
