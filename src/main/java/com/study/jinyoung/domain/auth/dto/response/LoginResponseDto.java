package com.study.jinyoung.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

    private Long userId;

    private String accessToken;

    private String refreshToken;

    public static LoginResponseDto of(Long memberId, String accessToken, String refreshToken) {
        return LoginResponseDto.builder()
                .userId(memberId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
