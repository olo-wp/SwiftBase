package com.swift.project.controllers;

import com.swift.project.DTOs.Message;
import com.swift.project.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BankRestControllerAdvice {
    @ExceptionHandler(BankNotFoundException.class)
    public final ResponseEntity<Message> bankNotFoundHandler(RuntimeException e) {
        return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BankAlreadyInDataBaseException.class)
    public final ResponseEntity<Message> bankAlreadyInBaseHandler(RuntimeException e) {
        return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({IllegalSwiftCodeException.class, IllegalISO2CodeException.class, IllegalSwiftCodeToHqMapping.class})
    public final ResponseEntity<Message> WrongSwiftCodeHandler(RuntimeException e) {
        return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
