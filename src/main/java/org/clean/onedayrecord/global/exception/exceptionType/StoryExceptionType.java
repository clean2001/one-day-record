package org.clean.onedayrecord.global.exception.exceptionType;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum StoryExceptionType implements ExceptionType {
    NO_VALID_STORY(HttpStatus.BAD_REQUEST, "유효한 스토리가 없습니다."),
    CANNOT_DELETE(HttpStatus.FORBIDDEN, "작성자만 글을 지울 수 있습니다.");

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