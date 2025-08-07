package com.careerthon.reactpractice.common.advice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
    private final String status;
    private final String code;
    private final String message;
    private final Integer httpStatus;
    private final T data;


    private ApiResponse(String status, String code, String message, Integer httpStatus, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
        this.data = data;
    }

    // 성공 응답
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", null, message, 200, data);
    }

    // 성공 응답 (아무 데이터 없음)
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>("success", null, message, 200, null);
    }

    // 실패 응답
    public static <T> ApiResponse<T> error(String code, String message, HttpStatus httpStatus, T data) {
        return new ApiResponse<>("error", code, message, httpStatus.value(), data);
    }

    // 실패 응답 (아무 데이터 없음)
    public static <T> ApiResponse<T> error(String code, String message, HttpStatus httpStatus) {
        return new ApiResponse<>("error", code, message, httpStatus.value(), null);
    }
}
