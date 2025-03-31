package com.swift.project.exceptions;

public class IllegalSwiftCodeException extends RuntimeException {
    public IllegalSwiftCodeException() {
        super("Swift Code must be 8-11 characters long");
    }
}
