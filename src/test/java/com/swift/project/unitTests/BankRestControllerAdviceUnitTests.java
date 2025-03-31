package com.swift.project.unitTests;

import com.swift.project.DTOs.Message;
import com.swift.project.controllers.BankRestControllerAdvice;
import com.swift.project.exceptions.BankAlreadyInDataBaseException;
import com.swift.project.exceptions.BankNotFoundException;
import com.swift.project.exceptions.IllegalSwiftCodeException;
import com.swift.project.other.Messages;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static com.swift.project.other.constants.SWIFT_CODE_IDENTIFIER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankRestControllerAdviceUnitTests {
    private final BankRestControllerAdvice advice = new BankRestControllerAdvice();

    @Test
    void testHandleBankNotFoundException() {
        BankNotFoundException exception = new BankNotFoundException(SWIFT_CODE_IDENTIFIER, "DOESNTEXIST");
        ResponseEntity<Message> response = advice.bankNotFoundHandler(exception);
        assertEquals(Messages.bankNotFoundExceptionErrorMessage(SWIFT_CODE_IDENTIFIER, "DOESNTEXIST"), response.getBody().getMessage());
    }

    @Test
    void testHandleBankAlreadyInBaseException() {
        BankAlreadyInDataBaseException exception = new BankAlreadyInDataBaseException("ALREADY_IN_DATABASE");
        ResponseEntity<Message> response = advice.bankNotFoundHandler(exception);
        assertEquals(Messages.bankAlreadyInDataBaseExceptionErrorMessage("ALREADY_IN_DATABASE"), response.getBody().getMessage());
    }

    @Test
    void testHandleWrongSwiftCodeException() {
        IllegalSwiftCodeException exception = new IllegalSwiftCodeException();
        ResponseEntity<Message> response = advice.bankNotFoundHandler(exception);
        assertEquals(Messages.illegalISO2CodeExceptionErrorMessage(), response.getBody().getMessage());
    }
}
