package com.careerthon.reactpractice.domain.user.dto;

import com.careerthon.reactpractice.domain.user.entity.UserRole;
import lombok.Getter;

@Getter
public class Authorities {
    private final UserRole authorityName;

    public Authorities(UserRole role) {
        this.authorityName = role;
    }
}
