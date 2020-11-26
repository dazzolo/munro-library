package com.davidebelpanno.munrolibrary.controller;

import com.davidebelpanno.munrolibrary.exceptions.NoMunrosFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoMunrosFoundAdvice {

    @ResponseBody
    @ExceptionHandler(NoMunrosFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    String noMunrosFoundHandler(NoMunrosFoundException ex) {
        return ex.getMessage();
    }

}
