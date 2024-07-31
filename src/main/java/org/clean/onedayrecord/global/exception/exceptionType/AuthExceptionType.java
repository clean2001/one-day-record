package org.clean.onedayrecord.global.exception.exceptionType;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthExceptionType implements ExceptionType {

    INVALID_SOCIAL_TYPE(HttpStatus.BAD_REQUEST, "소셜 로그인 타입이 유효하지 않습니다."),
    NO_VALID_MEMBER(HttpStatus.BAD_REQUEST, "유효한 유저가 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    @Override
    public org.springframework.http.HttpStatus getHttpStatus() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }
}
