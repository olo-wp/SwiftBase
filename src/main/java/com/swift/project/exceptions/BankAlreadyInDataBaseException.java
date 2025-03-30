package com.swift.project.exceptions;

public class BankAlreadyInDataBaseException extends RuntimeException {
    public BankAlreadyInDataBaseException(String swift) {
        super("Adding failed, bank with Swift Code: " + swift + " already in database");
    }
}