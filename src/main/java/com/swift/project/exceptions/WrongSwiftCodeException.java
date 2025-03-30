package com.swift.project.exceptions;

public class WrongSwiftCodeException extends RuntimeException {
    public WrongSwiftCodeException() {
        super("Swift Code must be 8-11 characters long");
    }
}
