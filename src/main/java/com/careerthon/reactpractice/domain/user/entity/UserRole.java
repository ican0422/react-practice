package com.careerthon.reactpractice.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ROLE_USER(AuthUser.USER),
    ROLE_ADMIN(AuthUser.ADMIN);

    private final String role;

    public static class AuthUser {
        private static final String USER = "USER";
        private static final String ADMIN = "ADMIN";
    }
}
