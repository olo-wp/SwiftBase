package com.swift.project.exceptions;

import com.swift.project.other.Messages;

public class BankAlreadyInDataBaseException extends RuntimeException {
    public BankAlreadyInDataBaseException(String swiftCode) {
        super(Messages.bankAlreadyInDataBaseExceptionErrorMessage(swiftCode));
    }
}