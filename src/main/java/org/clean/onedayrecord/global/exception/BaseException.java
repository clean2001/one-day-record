package org.clean.onedayrecord.global.exception;


import lombok.RequiredArgsConstructor;
import org.clean.onedayrecord.global.exception.exceptionType.ExceptionType;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    private final ExceptionType exceptionType;
}
