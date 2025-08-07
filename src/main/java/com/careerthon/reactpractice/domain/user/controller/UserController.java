package com.careerthon.reactpractice.domain.user.controller;

import com.careerthon.reactpractice.common.advice.ApiResponse;
import com.careerthon.reactpractice.domain.user.dto.request.UserLoginRequestDto;
import com.careerthon.reactpractice.domain.user.dto.request.UserSignupRequestDto;
import com.careerthon.reactpractice.domain.user.dto.respons.UserLoginResponseDto;
import com.careerthon.reactpractice.domain.user.dto.respons.UserSignupResponseDto;
import com.careerthon.reactpractice.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserSignupResponseDto>> signup(@RequestBody UserSignupRequestDto userSignupRequestDto) {
        UserSignupResponseDto user = service.signup(userSignupRequestDto);
        return ResponseEntity.ok(ApiResponse.success("회원가입 성공", user));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponseDto>> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        UserLoginResponseDto token = service.login(userLoginRequestDto);
        return ResponseEntity.ok(ApiResponse.success("로그인 성공", token));
    }
}
