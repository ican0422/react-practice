package com.careerthon.reactpractice.domain.user.dto.respons;

import com.careerthon.reactpractice.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserSignupResponseDto {
    private final String userId;

    public UserSignupResponseDto(User user) {
        this.userId = user.getUserId();
    }
}
