package com.study.jinyoung.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.study.jinyoung.domain.auth.dto.request.ReissueRequestDto;
import com.study.jinyoung.domain.auth.dto.response.LoginResponseDto;
import com.study.jinyoung.domain.auth.dto.response.RegisterResponseDto;
import com.study.jinyoung.domain.auth.dto.response.ReissueResponseDto;
import com.study.jinyoung.domain.user.entity.User;
import com.study.jinyoung.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.study.jinyoung.common.error.ErrorCode.EXPIRED_JWT_REFRESH_TOKEN;
import static com.study.jinyoung.common.error.ErrorCode.INVALID_JWT_REFRESH_TOKEN;
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

    public ReissueResponseDto reissue(ReissueRequestDto request) throws JsonProcessingException {
        Long userId = getUserIdFromAccessToken(request.getAccessToken());
        RefreshToken refreshToken = getRefreshTokenFromUserId(userId);
        validateRefreshTokenNotExpired(refreshToken);
        validateUserRefreshToken(refreshToken.getRefreshToken(), request.getRefreshToken());

        String accessToken = tokenProvider.createAccessToken(userId);
        updateNewRefreshToken(refreshToken);

        return ReissueResponseDto.of(accessToken, refreshToken.getRefreshToken());
    }

    private void updateNewRefreshToken(RefreshToken refreshToken) {
        String newRefreshToken = tokenProvider.createRefreshToken();
        refreshToken.update(newRefreshToken);
    }

    private void validateUserRefreshToken(String originalRefreshToken, String requestRefreshToken) {
        if(!originalRefreshToken.equals(requestRefreshToken)) {
            throw new UnauthorizedException(INVALID_JWT_REFRESH_TOKEN);
        }
    }

    private void validateRefreshTokenNotExpired(RefreshToken refreshToken) {
        tokenProvider.validateRefreshToken(refreshToken.getRefreshToken());
    }

    private RefreshToken getRefreshTokenFromUserId(Long userId) {
        return refreshTokenRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException(EXPIRED_JWT_REFRESH_TOKEN));
    }

    private Long getUserIdFromAccessToken(String accessToken) throws JsonProcessingException {
        return Long.parseLong(tokenProvider.decodeJwtPayloadSubject(accessToken));
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
            originalRefreshToken.update(newRefreshToken);
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
