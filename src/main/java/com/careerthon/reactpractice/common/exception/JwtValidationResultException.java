package com.careerthon.reactpractice.common.exception;

import com.careerthon.reactpractice.common.entity.JwtExceptionConst;

public class JwtValidationResultException extends GlobalException {
    public JwtValidationResultException(String message, JwtExceptionConst status) {
        super(message, status);
    }
    public JwtValidationResultException(String message, JwtExceptionConst status, Throwable e) {
        super(message, status);
        initCause(e);
    }
}
