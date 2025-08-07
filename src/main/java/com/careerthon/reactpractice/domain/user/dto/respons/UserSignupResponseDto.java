package com.careerthon.reactpractice.domain.user.dto.respons;

import lombok.Getter;

@Getter
public class UserSignupResponseDto {
    private final String userId;

    public UserSignupResponseDto(String userId) {
        this.userId = userId;
    }
}
