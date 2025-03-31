package com.swift.project.exceptions;

import com.swift.project.other.Messages;

public class IllegalSwiftCodeException extends RuntimeException {
    public IllegalSwiftCodeException() {
        super(Messages.illegalSwiftCodeExceptionErrorMessage());
    }
}
