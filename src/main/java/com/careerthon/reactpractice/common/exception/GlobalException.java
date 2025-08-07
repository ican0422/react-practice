package com.careerthon.reactpractice.common.exception;

import lombok.Getter;

// 애플리케이션 전역에서 사용하는 공통 예외 클래스
// 각 도메인 예외는 GlobalException을 상속함으로써 전역 핸들러에서 일관된 방식으로 처리
@Getter
public class GlobalException extends RuntimeException {
    private final BaseExceptionConst exceptionConst;
    private final Object data;

    public GlobalException(String message, BaseExceptionConst exceptionConst) {
        this(message, exceptionConst, null);
    }

    public GlobalException(String message, BaseExceptionConst exceptionConst, Object data) {
        super(message);
        this.exceptionConst = exceptionConst;
        this.data = data;
    }
}
