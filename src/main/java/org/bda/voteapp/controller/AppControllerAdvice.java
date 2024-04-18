package org.bda.voteapp.controller;

import org.bda.voteapp.util.IllegalRequestDataException;
import org.bda.voteapp.util.NotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class AppControllerAdvice {
    @ExceptionHandler({NoSuchElementException.class, NotFoundException.class, NoResourceFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object notFoundHandler(Exception e) {
        return new HttpEntity<>("Resource not found");
    }

    @ExceptionHandler(IllegalRequestDataException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Object illegalRequestDataHandler(Exception e) {
        return new HttpEntity<>(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object badRequestHandler(Exception e) {
        return new HttpEntity<>(e.getMessage());
    }
}
