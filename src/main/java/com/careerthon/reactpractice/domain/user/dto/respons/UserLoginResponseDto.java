package com.careerthon.reactpractice.domain.user.dto.respons;

import lombok.Getter;

@Getter
public class UserLoginResponseDto {
    private final String token;

    public UserLoginResponseDto(String token) {
        this.token = token;
    }
}
