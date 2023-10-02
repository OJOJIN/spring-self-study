package com.study.jinyoung.domain.member.controller;

import com.study.jinyoung.common.dto.SuccessResponse;
import com.study.jinyoung.common.dto.code.SuccessCode;
import com.study.jinyoung.domain.member.dto.request.CreateSampleRequestDto;
import com.study.jinyoung.domain.member.dto.response.CreateSampleResponseDto;
import com.study.jinyoung.domain.member.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.success(SuccessCode.CREATED, response));
    }
}
