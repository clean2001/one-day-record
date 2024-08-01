package org.clean.onedayrecord.global.etc;

import lombok.extern.slf4j.Slf4j;
import org.clean.onedayrecord.global.exception.BaseException;
import org.clean.onedayrecord.global.exception.exceptionType.ExceptionType;
import org.clean.onedayrecord.global.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        ExceptionType exceptionType = e.getExceptionType();
        ErrorResponse error = ErrorResponse.builder()
                .message(e.getMessage())
                .code(exceptionType.getHttpStatus().value())
                .build();

        return ResponseEntity.status(exceptionType.getHttpStatus()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(Exception e) {
        log.error(String.format("%s", e.getMessage()), e);

        ErrorResponse error = ErrorResponse.builder()
                .message("서버 에러입니다.")
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
