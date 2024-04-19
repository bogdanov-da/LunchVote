package org.bda.voteapp.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.bda.voteapp.util.IllegalRequestDataException;
import org.bda.voteapp.util.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.util.*;

@ControllerAdvice
public class AppControllerAdvice {
    @ExceptionHandler({NoSuchElementException.class, NotFoundException.class})
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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object constraintViolationExceptionHandler(ConstraintViolationException e) {
        return new HttpEntity<>(e.getConstraintViolations().stream().map(ConstraintViolation::getMessageTemplate));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Object dataIntegrityViolationExceptionHandler(SQLException e) {
        return new HttpEntity<>(e.getNextException().getMessage());
    }

}
