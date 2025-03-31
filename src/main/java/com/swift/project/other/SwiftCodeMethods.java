package com.swift.project.other;

import com.swift.project.exceptions.IllegalSwiftCodeException;

import static com.swift.project.other.constants.HQ_SUFFIX;

public class SwiftCodeMethods {
    public static String getCommonPrefix(String swiftCode) {
        checkSwiftCodeLength(swiftCode);
        return swiftCode.substring(0, 8);
    }

    public static void checkSwiftCodeLength(String swiftCode) throws IllegalSwiftCodeException {
        if (swiftCode == null || swiftCode.length() < 8 || swiftCode.length() > 11) throw new IllegalSwiftCodeException();
    }

    public static boolean representsHQ(String swiftCode) throws IllegalSwiftCodeException {
        checkSwiftCodeLength(swiftCode);
        return swiftCode.endsWith(HQ_SUFFIX);
    }
}
