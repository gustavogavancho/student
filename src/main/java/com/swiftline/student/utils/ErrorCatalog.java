package com.swiftline.student.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ErrorCatalog {

    STUDENT_NOT_FOUND("ERR_STUDENT_001", "Student not found"),
    INVALID_STUDENT("ERR_STUDENT_002", "Invalid students parameters"),
    GENERIC_ERROR("ERR_GENERIC_001", "An unexpected error ocurred");

    private final String code;
    private final String message;

    ErrorCatalog(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
