package com.careerthon.reactpractice.domain.user.exception;
import com.careerthon.reactpractice.common.exception.GlobalException;
import lombok.Getter;

@Getter
public class UserException extends GlobalException {
    public UserException(String message, UserExceptionConst status) {
        super(message, status);
    }
}
