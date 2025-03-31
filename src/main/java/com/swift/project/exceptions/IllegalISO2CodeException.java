package com.swift.project.exceptions;

import com.swift.project.other.Messages;

public class IllegalISO2CodeException extends RuntimeException {
    public IllegalISO2CodeException() {
        super(Messages.illegalISO2CodeExceptionErrorMessage());
    }
}
