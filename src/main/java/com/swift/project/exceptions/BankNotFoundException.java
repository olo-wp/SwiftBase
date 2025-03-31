package com.swift.project.exceptions;

import com.swift.project.other.Messages;

public class BankNotFoundException extends RuntimeException {
    public BankNotFoundException(String identifier, String identifierID) {
        super(Messages.bankNotFoundExceptionErrorMessage(identifier, identifierID));
    }
}