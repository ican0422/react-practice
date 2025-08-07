package com.careerthon.reactpractice.domain.user.repository;

import com.careerthon.reactpractice.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserId(String userId);

    Optional<User> findByUserId(String userId);
}
