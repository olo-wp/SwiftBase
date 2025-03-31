package com.swift.project.exceptions;

public class IllegalISO2CodeException extends RuntimeException{
    public IllegalISO2CodeException(){
        super("ISO2 code must be exactly 2 characters long");
    }
}
