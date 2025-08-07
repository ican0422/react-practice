package com.careerthon.reactpractice.common.entity;

import com.careerthon.reactpractice.common.exception.BaseExceptionConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtExceptionConst implements BaseExceptionConst {
    // 토큰이 비어 있습니다, JWT 토큰이 비어 있습니다, 401
    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED),
    // 토큰 만료, 401
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED),
    // 토큰 서명이 일치하지 않습니다, 401
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED),
    // 토큰 구문 분석 실패, 400
    MALFORMED_TOKEN(HttpStatus.BAD_REQUEST),
    // 엑세스 토큰이 아닙니다, 401
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED),
    // 클레임을 추출하는 동안 예상치 못한 오류 발생, 500
    CLAIM_EXTRACTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);


    private final HttpStatus httpStatus;
}
