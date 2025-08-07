package com.careerthon.reactpractice.domain.user.service;

import com.careerthon.reactpractice.common.config.JwtUtils;
import com.careerthon.reactpractice.common.config.PasswordEncoder;
import com.careerthon.reactpractice.domain.user.dto.request.UserLoginRequestDto;
import com.careerthon.reactpractice.domain.user.dto.request.UserSignupRequestDto;
import com.careerthon.reactpractice.domain.user.dto.respons.UserLoginResponseDto;
import com.careerthon.reactpractice.domain.user.dto.respons.UserSignupResponseDto;
import com.careerthon.reactpractice.domain.user.entity.User;
import com.careerthon.reactpractice.domain.user.entity.UserRole;
import com.careerthon.reactpractice.domain.user.exception.UserException;
import com.careerthon.reactpractice.domain.user.exception.UserExceptionConst;
import com.careerthon.reactpractice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    /* 회원가입 */
    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto requestDto) {
        // 가입 됐는지 검증
        if(userRepo.existsByUserId(requestDto.getUserId())) {
            throw new UserException("이미 가입된 유저 입니다.", UserExceptionConst.DUPLICATE_USER);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 엔티티로 바꾼다.
        User user = User.builder()
                .userId(requestDto.getUserId())
                .username(requestDto.getUsername())
                .nickname(requestDto.getNickname())
                .password(encodedPassword)
                .userRole(UserRole.ROLE_USER)
                .build();

        // DB 저장
        User saveUser = userRepo.save(user);

        // 리턴
        return new UserSignupResponseDto(saveUser);
    }

    /* 로그인 */
    @Transactional(readOnly = true)
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        // 회원가입 되어 있는지 확인 -> 가입되어 있으면 정보를 가져온다.
        User user = userRepo.findByUserId(requestDto.getUserId())
                .orElseThrow(() -> new UserException("회원 정보를 찾을 수 없습니다.", UserExceptionConst.USER_NOT_FOUND));

        // 비밀번호 확인
        boolean verify = passwordEncoder.verify(requestDto.getPassword(), user.getPassword());
        if(!verify) {
            throw new UserException("잘못 된 비밀번호 입니다.", UserExceptionConst.INVALID_PASSWORD);
        }

        // 토큰 생성
        String token = jwtUtils.createToken(
                user.getId(),
                user.getUserId(),
                user.getUsername(),
                user.getNickname(),
                user.getUserRole()
        );

        // 토큰 반환
        return new UserLoginResponseDto(token);
    }
}
