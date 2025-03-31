package com.swift.project.other;

public class Messages {
    public static String illegalISO2CodeExceptionErrorMessage() {
        return "Swift Code must be 8-11 characters long";
    }

    public static String illegalSwiftCodeExceptionErrorMessage() {
        return "Swift Code must be 8-11 characters long";
    }

    public static String bankNotFoundExceptionErrorMessage(String identifier, String identifierID) {
        return "Could not find bank with " + identifier + " " + identifierID;
    }

    public static String bankAlreadyInDataBaseExceptionErrorMessage(String swiftCode) {
        return "Adding failed, bank with Swift Code: " + swiftCode + " already in database";
    }

    public static String bankSuccessfullyAdded(String swiftCode) {
        return "Bank with Swift Code: " + swiftCode + " has been successfully added";
    }

    public static String bankSuccessfullyDeleted(String swiftCode) {
        return "Bank with Swift Code: " + swiftCode + " has been successfully deleted";
    }

    public static String illegalSwiftCodeToHqMappingErrorMessage() {
        return "Bank can be a Headquarter if and only if it's Swift Code end with XXX";
    }
}
