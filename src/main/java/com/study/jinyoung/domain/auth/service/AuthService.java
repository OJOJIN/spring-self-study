package com.study.jinyoung.domain.auth.service;

import com.study.jinyoung.common.error.ErrorCode;
import com.study.jinyoung.common.error.DuplicateException;
import com.study.jinyoung.common.error.EntityNotFoundException;
import com.study.jinyoung.common.error.UnauthorizedException;
import com.study.jinyoung.common.jwt.TokenProvider;
import com.study.jinyoung.common.jwt.dto.Token;
import com.study.jinyoung.common.redis.entity.RefreshToken;
import com.study.jinyoung.common.redis.repository.RefreshTokenRepository;
import com.study.jinyoung.domain.auth.dto.request.LoginRequestDto;
import com.study.jinyoung.domain.auth.dto.request.RegisterRequestDto;
import com.study.jinyoung.domain.auth.dto.response.LoginResponseDto;
import com.study.jinyoung.domain.auth.dto.response.RegisterResponseDto;
import com.study.jinyoung.domain.user.entity.User;
import com.study.jinyoung.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.study.jinyoung.domain.user.entity.User.createUser;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public RegisterResponseDto register(RegisterRequestDto request) {
        String encodedPassword = encodePassword(request.getPassword());
        validateDuplicateUsername(request.getUsername());
        User user = createUser(request.getUsername(), encodedPassword);
        User savedUser = userRepository.save(user);
        return RegisterResponseDto.of(savedUser.getId());
    }

    public LoginResponseDto login(LoginRequestDto request) {
        User user = getUserByUserName(request.getUsername());
        validateUserPassword(request.getPassword(), user.getPassword());
        Token token = generateToken(user.getId());
        String refreshToken = saveRefreshToken(user.getId(), token.getRefreshToken());
        return LoginResponseDto.of(user.getId(), token.getAccessToken(), refreshToken);
    }

    private String encodePassword(String originalPassword) {
        return passwordEncoder.encode(originalPassword);
    }

    private User getUserByUserName(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private void validateUserPassword(String inputPassword, String encodedPassword) {
        if(!passwordEncoder.matches(inputPassword, encodedPassword)) {
            throw new UnauthorizedException(ErrorCode.WRONG_USER_PASSWORD);
        }
    }

    private void validateDuplicateUsername(String username) {
        if(userRepository.existsByUsername(username)){
            throw new DuplicateException(ErrorCode.DUPLICATE_USERNAME);
        }
    }

    private Token generateToken(Long userId) {
        return Token.of(tokenProvider.createAccessToken(userId), tokenProvider.createRefreshToken());
    }

    private String saveRefreshToken(Long userId, String newRefreshToken) {
        // 기존에 refreshToken이 redis에 들어있는지 확인
        if(refreshTokenRepository.existsById(userId)) {
            RefreshToken originalRefreshToken = refreshTokenRepository.findById(userId).get();
            // 일주일 이하로 refresh 토큰의 만료일이 남아있는지 확인
            // 너무 자주 refresh 토큰을 갈아주는 것도 코스트기에 로그인시 리프레시 토큰의 기한이 1주일 이상인지 확인
            // 1주일 이하일 때만 새로운 토큰으로 교체해줌
            if(validateRefreshTokenRemainDayUnderOneWeek(originalRefreshToken.getRefreshToken())) {
                originalRefreshToken.update(newRefreshToken);
            }
            return originalRefreshToken.getRefreshToken();
        }
        // redis에 refreshToken이 담겨있지 않다면 바로 저장
        else {
            refreshTokenRepository.save(RefreshToken.createRefreshToken(userId, newRefreshToken));
        }
        return newRefreshToken;
    }

    private Boolean validateRefreshTokenRemainDayUnderOneWeek(String refreshToken) {
        Long expireTime = tokenProvider.getExpDateFromToken(refreshToken);
        return (expireTime * 1000 - System.currentTimeMillis()) / (24 * 3600000) < 7;
    }
}
