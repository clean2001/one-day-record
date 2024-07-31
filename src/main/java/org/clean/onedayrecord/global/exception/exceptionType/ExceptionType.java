package org.clean.onedayrecord.global.exception.exceptionType;

import org.springframework.http.HttpStatus;

public interface ExceptionType {
    HttpStatus getHttpStatus();
    String getErrorMessage();
}
