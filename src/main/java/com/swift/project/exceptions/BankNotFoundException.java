package com.swift.project.exceptions;

public class BankNotFoundException extends RuntimeException{
    public BankNotFoundException(String identifierID, String identifier) {
        super("Could not find bank with " + identifier + " " + identifierID);
    }
}