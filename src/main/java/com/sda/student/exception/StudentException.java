package com.sda.student.exception;

import org.springframework.web.server.WebExceptionHandler;

public class StudentException extends RuntimeException {

    private ExceptionType exceptionType;

    public StudentException() {
        super();
    }

    public StudentException(String message, ExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;

    }
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }
    public enum ExceptionType {
        INVALID_FIELD
    }
}
