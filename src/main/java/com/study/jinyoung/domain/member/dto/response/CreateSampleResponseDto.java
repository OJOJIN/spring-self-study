package com.study.jinyoung.domain.member.dto.response;

import com.study.jinyoung.domain.member.dto.request.CreateSampleRequestDto;
import com.study.jinyoung.domain.member.entity.Sample;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateSampleResponseDto {
    private String text;

    public static CreateSampleResponseDto of(Sample sample) {
        return CreateSampleResponseDto.builder()
                .text(sample.getText())
                .build();
    }
}
