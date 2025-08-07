package com.careerthon.reactpractice.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignupRequestDto {
    private String userId;
    private String username;
    private String nickname;
    private String password;
}
