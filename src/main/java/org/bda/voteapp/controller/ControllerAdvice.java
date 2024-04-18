package org.bda.voteapp.controller;

import org.bda.voteapp.util.IllegalRequestDataException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object notFoundHandler(Exception e) {
        return new HttpEntity<>("Resource not found");
    }

    @ExceptionHandler(IllegalRequestDataException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Object illegalRequestDataHandler(Exception e) {
        return new HttpEntity<>(e.getMessage());
    }
}
