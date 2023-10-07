package com.study.jinyoung.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponseDto {

    private Long userId;

    public static RegisterResponseDto of(Long memberId) {
        return RegisterResponseDto.builder()
                .userId(memberId)
                .build();
    }
}
