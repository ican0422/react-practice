package com.careerthon.reactpractice.common.advice;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final String status;
    private final String message;
    private final T data;

    private ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // 성공 응답
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", message, data);
    }

    // 성공 응답 (아무 데이터 없음)
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>("success", message, null);
    }

    // 실패 응답
    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>("error", message, data);
    }

    // 실패 응답 (아무 데이터 없음)
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("error", message, null);
    }
}
