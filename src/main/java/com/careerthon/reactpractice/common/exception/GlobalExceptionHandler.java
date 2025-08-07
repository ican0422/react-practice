package com.careerthon.reactpractice.common.exception;

import com.careerthon.reactpractice.common.advice.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ApiResponse<?>> handleGlobalException(GlobalException e) {
        return ResponseEntity.status(e.getExceptionConst().getHttpStatus())
                .body(ApiResponse.error(
                        e.getExceptionConst().name(),
                        e.getMessage(),
                        e.getExceptionConst().getHttpStatus(),
                        e.getData()
                        )
                );
    }
}
