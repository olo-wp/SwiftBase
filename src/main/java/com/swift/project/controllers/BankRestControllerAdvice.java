package com.swift.project.controllers;

import com.swift.project.exceptions.BankAlreadyInDataBaseException;
import com.swift.project.exceptions.BankNotFoundException;
import com.swift.project.exceptions.WrongSwiftCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BankRestControllerAdvice {
    @ExceptionHandler(BankNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bankNotFoundHandler(RuntimeException e){
        return e.getMessage();
    }

    @ExceptionHandler(BankAlreadyInDataBaseException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String bankAlreadyInBaseHandler(RuntimeException e){return e.getMessage();}

    @ExceptionHandler(WrongSwiftCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String WrongSwiftCodeHandler(RuntimeException e){return e.getMessage();}


}
