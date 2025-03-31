package com.swift.project.unitTests;

import com.swift.project.controllers.BankRestControllerAdvice;
import com.swift.project.exceptions.BankAlreadyInDataBaseException;
import com.swift.project.exceptions.BankNotFoundException;
import com.swift.project.exceptions.WrongSwiftCodeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankRestControllerAdviceUnitTests {
    private final BankRestControllerAdvice advice = new BankRestControllerAdvice();


    @Test
    void testHandleBankNotFoundException() {
        BankNotFoundException exception = new BankNotFoundException("DOESNTEXIST", "Swift Code");
        String response = advice.bankNotFoundHandler(exception);
        String testBankNotFoundExceptionErrorMessage = "Could not find bank with Swift Code DOESNTEXIST";
        assertEquals(testBankNotFoundExceptionErrorMessage, response);
    }

    @Test
    void testHandleBankAlreadyInBaseException() {
        BankAlreadyInDataBaseException exception = new BankAlreadyInDataBaseException("ALREADY_IN_DATABASE");
        String response = advice.bankAlreadyInBaseHandler(exception);
        String testHandleBankAlreadyInBaseExceptionErrorMessage = "Adding failed, bank with Swift Code: ALREADY_IN_DATABASE already in database";
        assertEquals(testHandleBankAlreadyInBaseExceptionErrorMessage, response);
    }

    @Test
    void testHandleWrongSwiftCodeException() {
        WrongSwiftCodeException exception = new WrongSwiftCodeException();
        String response = advice.WrongSwiftCodeHandler(exception);
        String testHandleWrongSwiftCodeExceptionErrorMessage = "Swift Code must be 8-11 characters long";
        assertEquals(testHandleWrongSwiftCodeExceptionErrorMessage, response);
    }
}
