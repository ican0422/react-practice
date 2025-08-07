package com.careerthon.reactpractice.common.exception;

import org.springframework.http.HttpStatus;

// 모든 도메인 예외 코드 Enum이 구현해야 하는 공통 인터페이스
// 인터페이스를 통해 GlobalException이 모든 도메인 에러 코드를 일관되게 처리
public interface BaseExceptionConst {
    HttpStatus getHttpStatus();
    String name();
}
