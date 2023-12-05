package com.study.jinyoung.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReissueResponseDto {

    private String accessToken;

    private String refreshToken;


    public static ReissueResponseDto of(String accessToken, String refreshToken) {
        return ReissueResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
