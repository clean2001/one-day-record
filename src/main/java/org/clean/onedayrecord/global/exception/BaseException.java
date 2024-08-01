package org.clean.onedayrecord.global.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.clean.onedayrecord.global.exception.exceptionType.ExceptionType;

@Getter
@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    private final ExceptionType exceptionType;
}
