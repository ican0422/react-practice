package com.careerthon.reactpractice.domain.user.exception;

import com.careerthon.reactpractice.common.exception.BaseExceptionConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionConst implements BaseExceptionConst {
    // 이미 가입된 유저, 409
    DUPLICATE_USER(HttpStatus.CONFLICT),
    // 회원 정보를 찾을 수 없습니다, 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND),
    // 잘못된 비밀번호 입니다, 401
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED);

    private final HttpStatus httpStatus;
}
