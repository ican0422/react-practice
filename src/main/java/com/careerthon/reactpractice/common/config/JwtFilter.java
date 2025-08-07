package com.careerthon.reactpractice.common.config;

import com.careerthon.reactpractice.common.advice.ApiResponse;
import com.careerthon.reactpractice.common.entity.JwtExceptionConst;
import com.careerthon.reactpractice.common.exception.JwtValidationResultException;
import com.careerthon.reactpractice.domain.user.dto.AuthUser;
import com.careerthon.reactpractice.domain.user.entity.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain
    ) throws ServletException, IOException {

        String url = request.getRequestURI();

        // 토큰 검사 패스 경로 설정
        if (
                url.equals("/api/auth/signup")
                || url.equals("/api/auth/login")
        ) {
            chain.doFilter(request, response); // JWT 토큰 검사 제외
            return;
        }

        try {
        // Authorization 헤더에서 토큰 추출
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            throw new JwtValidationResultException("JWT 토큰이 없습니다.", JwtExceptionConst.EMPTY_TOKEN);
        }

            // 토큰 검증
            //  토큰 추출
            String token = jwtUtil.substringToken(tokenHeader)
                    .orElseThrow(() -> new JwtValidationResultException("JWT 토큰이 비어 있습니다.", JwtExceptionConst.EMPTY_TOKEN));
            // 클레임 검증
            Claims claims = jwtUtil.validateAndExtractClaims(token);

            // tokenType 검증 (엑세스 토큰인지 검증)
            String tokenType = claims.get("tokenType", String.class);
            if (!"access".equals(tokenType)) {
                throw new JwtValidationResultException("엑세스 토큰이 아닙니다.", JwtExceptionConst.INVALID_TOKEN_TYPE);
            }

            // 사용자 정보 추출
            Long userId = Long.parseLong(claims.getSubject()); // userId 추출
            UserRole userRole = UserRole.valueOf(claims.get("role", String.class));

            // 인증되지 않았을때만 작동
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // 인증 객체 생성
                AuthUser authUser = new AuthUser(userId, userRole);

                // 권한 추출
                Collection<? extends GrantedAuthority> authorities = authUser.getUserRole();

                // Authentication 객체 생성
                Authentication Authentication = new JwtAuthenticationToken(authUser, null, authorities);

                // SecurityContext에 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(Authentication);
            }

            log.info("JWT 검증 성공, 클레임: {}", claims);
            chain.doFilter(request, response);
        } catch (JwtValidationResultException e) {
            log.warn("JWT 검증 실패: {}", e.getMessage(), e.getExceptionConst().name());
            handleForbiddenResponse(response, e);
        } catch (Exception e) {
            log.error("JWT 필터 처리 중 오류 발생: {}", e.getMessage());
            handleInternalServerError(response, "서버 내부 오류가 발생했습니다.");
        }
    }

    // 필터 검사에서 걸린 경우
    private void handleForbiddenResponse(HttpServletResponse response, JwtValidationResultException e) throws IOException {
        // 상태 코드 가져오기
        HttpStatus status = e.getExceptionConst().getHttpStatus();

        // 응답 설정
        response.setStatus(status.value());
        // JSON 응답 형식 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ApiResponse 구성: code는 enum name
        ApiResponse<Object> error = ApiResponse.error(
                e.getExceptionConst().name(),   // enum 상수 이름
                e.getMessage(),                 // 오류 메세지
                status                          // http 상태 코드
        );

        // 클라이언트에게 에러 메시지 전송
        String json = new ObjectMapper().writeValueAsString(error);
        response.getWriter().write(json);
        response.getWriter().flush();
    }

    private void handleInternalServerError(HttpServletResponse response, String message) throws IOException {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiResponse<Object> errorResponse = ApiResponse.error(
                "INTERNAL_SERVER_ERROR",  // 고정된 에러 코드
                message,                  // 사용자 정의 메시지
                status
        );

        String json = new ObjectMapper().writeValueAsString(errorResponse);

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}
