package com.swift.project.service;

import com.swift.project.exceptions.BankAlreadyInBaseException;
import com.swift.project.exceptions.BankNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BankNotFoundAdvice {
    @ExceptionHandler({BankAlreadyInBaseException.class, BankNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bankNotFoundHandler(RuntimeException e){
        return e.getMessage();
    }
}
