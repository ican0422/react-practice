package com.careerthon.reactpractice.common.config;

import com.careerthon.reactpractice.common.exception.JwtValidationResultException;
import com.careerthon.reactpractice.domain.user.entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class JwtUtils {
    private final SecretKey key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private static final String BEARER = "Bearer ";
    private static final long TOKEN_EXPIRATION_TIME = 5 * 60 * 1000L; // 60분 동안 사용

    // Jwt 키 초기화
    public JwtUtils(@Value("${JWT_SECRET_KEY}")String secretKey) {
        // Base 64 디코딩 추가
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);

        // 키 생성
        this.key = Keys.hmacShaKeyFor(decodedKey);

        // 키 확인용 인포
        log.info("JWT_SECRET_KEY: {}", this.key);
    }

    // JWT 토큰만 추출
    public Optional<String> substringToken(String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith(BEARER)) {
            return Optional.empty();
        }
        return Optional.of(tokenHeader.substring(BEARER.length()));
    }

    // 클레임 추출
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 유효성 검사 + 클레임 추출 통합 메서드
    public Claims validateAndExtractClaims(String token) {
        // 토큰이 비어 있는 경우
        if (token == null || token.trim().isEmpty()) {
            throw new JwtValidationResultException("토큰이 비어 있습니다.");
        }

        try {
            Claims claims = extractClaims(token);
            // 성공 로그
            log.info("JWT 클레임 성공적으로 추출: {}", claims);
            return claims;
        } catch (ExpiredJwtException e) {
            throw new JwtValidationResultException("토큰 만료", e);
        } catch (SignatureException e) {
            throw new JwtValidationResultException("토큰 서명이 일치하지 않습니다.", e);
        } catch (JwtException e) {
            throw new JwtValidationResultException("토큰 구문 분석 실패", e);
        } catch (Exception e) {
            throw new JwtValidationResultException("클레임을 추출하는 동안 예상치 못한 오류 발생", e);
        }
    }

    // 토큰 생성
    public String createToken(Long id, String userId, String username, String nickname, UserRole userRole) {
        // 생성 시간
        Date now = new Date();

        // 만료 시간
        Date expiryDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);

        // 토큰 설정
        return Jwts.builder()
                .setSubject(id.toString())                // 사용자 ID
                .claim("userId", userId)               // 유저 아이디
                .claim("userName", username)          // 사용자 이름
                .claim("nickName", nickname)          // 닉네임
                .claim("userRole", userRole)          // 권한
                .claim("tokenType", "access")     // 토큰 타입 (엑세스 토큰)
                .setExpiration(expiryDate)              // 만료 시간
                .setIssuedAt(now)                       // 발급 시간
                .signWith(key, signatureAlgorithm)      // 서명 설정
                .compact();                             // 문자열 반환
    }
}
