package com.careerthon.reactpractice.domain.user.exception;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException {
    private final UserExceptionConst status;

    public UserException(String message, UserExceptionConst status) {
        super(message);
        this.status = status;
    }
}
