package com.swift.project;

import com.swift.project.exceptions.WrongSwiftCodeException;
import com.swift.project.other.SwiftCodeMethods;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SwiftCodeMethodsUnitTests {
    @Test
    void testCheckSwiftCodeLenght(){
        String swiftCode = "1234567";
        assertThrows(WrongSwiftCodeException.class, () -> SwiftCodeMethods.checkSwiftCodeLength(swiftCode));
        String swiftCode2 = "1234567890123";
        assertThrows(WrongSwiftCodeException.class, () -> SwiftCodeMethods.checkSwiftCodeLength(swiftCode2));
        assertThrows(WrongSwiftCodeException.class, () -> SwiftCodeMethods.checkSwiftCodeLength(null));
        int i = 8;
        while(i < 12){
            StringBuilder swiftCodeBuilder = new StringBuilder();
            swiftCodeBuilder.setLength(i);
            assertDoesNotThrow(() -> SwiftCodeMethods.checkSwiftCodeLength(swiftCodeBuilder.toString()));
            ++i;
        }
    }

    @Test
    void testGetCommonPrefix(){
        String swiftCode = "1234567";
        assertThrows(WrongSwiftCodeException.class, () -> SwiftCodeMethods.getCommonPrefix(swiftCode));
        assertThrows(WrongSwiftCodeException.class, () -> SwiftCodeMethods.getCommonPrefix(null));
        String swiftCode2 = "12345678";
        String swiftCode3 = "12345678XXX";
        assertEquals(SwiftCodeMethods.getCommonPrefix(swiftCode3), SwiftCodeMethods.getCommonPrefix(swiftCode2));
    }

    @Test
    void testRepresentsHq(){
        String swiftCode = "1234567";
        assertThrows(WrongSwiftCodeException.class, () -> SwiftCodeMethods.representsHQ(swiftCode));
        assertThrows(WrongSwiftCodeException.class, () -> SwiftCodeMethods.representsHQ(null));
        String swiftCode2 = "12345678";
        String swiftCode3 = "12345678XXX";
        assertFalse(SwiftCodeMethods.representsHQ(swiftCode2));
        assertTrue(SwiftCodeMethods.representsHQ(swiftCode3));
    }
}
