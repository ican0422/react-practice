package com.careerthon.reactpractice.domain.user.entity;

import com.careerthon.reactpractice.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "User")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String username;
    private String nickname;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
