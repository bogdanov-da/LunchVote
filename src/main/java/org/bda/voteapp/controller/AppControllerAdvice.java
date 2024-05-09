package org.bda.voteapp.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.bda.voteapp.exception.IllegalRequestDataException;
import org.bda.voteapp.exception.NotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class AppControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object notFoundExceptionHandler(Exception e) {
        return new HttpEntity<>(e.getMessage());
    }

    @ExceptionHandler(IllegalRequestDataException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Object illegalRequestExceptionDataHandler(Exception e) {
        return new HttpEntity<>(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object badRequestExceptionHandler(Exception e) {
        return new HttpEntity<>(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object constraintViolationExceptionHandler(ConstraintViolationException e) {
        return new HttpEntity<>(e.getConstraintViolations().stream().map(ConstraintViolation::getMessageTemplate));
    }
}
