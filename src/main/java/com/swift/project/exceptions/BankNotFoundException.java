package com.swift.project.exceptions;

public class BankNotFoundException extends RuntimeException{
    public BankNotFoundException(String s, String identifier){
        super("Could not find bank with " + identifier + " " + s);
    }
}
