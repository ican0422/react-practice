package com.careerthon.reactpractice.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequestDto {
    private String userId;
    private String password;
}
