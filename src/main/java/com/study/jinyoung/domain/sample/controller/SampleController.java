package com.study.jinyoung.domain.sample.controller;

import com.study.jinyoung.common.dto.SuccessResponse;
import com.study.jinyoung.common.dto.code.SuccessCode;
import com.study.jinyoung.domain.sample.dto.request.CreateSampleRequestDto;
import com.study.jinyoung.domain.sample.dto.response.CreateSampleResponseDto;
import com.study.jinyoung.domain.sample.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/samples")
public class SampleController {

    private final SampleService sampleService;

    @PostMapping
    public ResponseEntity<SuccessResponse<CreateSampleResponseDto>> createSample(@RequestBody CreateSampleRequestDto createSample) {
        CreateSampleResponseDto response = sampleService.createSample(createSample.getText());
        return SuccessResponse.of(SuccessCode.CREATED, response);
    }
}
