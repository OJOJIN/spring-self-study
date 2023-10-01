package com.study.jinyoung.domain.member.controller;

import com.study.jinyoung.common.dto.SuccessResponse;
import com.study.jinyoung.common.dto.code.SuccessCode;
import com.study.jinyoung.domain.member.dto.request.CreateSampleRequestDto;
import com.study.jinyoung.domain.member.dto.response.CreateSampleResponseDto;
import com.study.jinyoung.domain.member.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/samples")
public class SampleController {

    private final SampleService sampleService;

    @GetMapping
    public SuccessResponse<String> test() {
        return SuccessResponse.success(SuccessCode.OK, "test OK");
    }

    @PostMapping
    public SuccessResponse<CreateSampleResponseDto> createSample(@RequestBody CreateSampleRequestDto createSample) {
        return SuccessResponse.success(SuccessCode.CREATED, sampleService.createSample(createSample.getText()));
    }
}
