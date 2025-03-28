package com.swift.project.exceptions;

public class BankAlreadyInBaseException extends RuntimeException{
    public BankAlreadyInBaseException(String swift){
        super("Adding failed, bank with Swift Code: " + swift + " already in database");
    }
}
