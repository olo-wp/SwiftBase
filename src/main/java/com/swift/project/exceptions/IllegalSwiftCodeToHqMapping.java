package com.swift.project.exceptions;

import com.swift.project.other.Messages;

public class IllegalSwiftCodeToHqMapping extends RuntimeException {
    public IllegalSwiftCodeToHqMapping() {
        super(Messages.illegalSwiftCodeToHqMappingErrorMessage());
    }
}
