package com.study.jinyoung.domain.member.service;

import com.study.jinyoung.common.error.DuplicateException;
import com.study.jinyoung.domain.member.dto.response.CreateSampleResponseDto;
import com.study.jinyoung.domain.member.entity.Sample;
import com.study.jinyoung.domain.member.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.study.jinyoung.common.error.ApplicationError.*;

@RequiredArgsConstructor
@Service
@Transactional
public class SampleService {

    private final SampleRepository sampleRepository;

    public CreateSampleResponseDto createSample(String text) {
        validateDuplicateText(text);
        Sample sample = saveText(text);
        return CreateSampleResponseDto.of(sample);
    }

    private void validateDuplicateText(String text) {
        if(sampleRepository.existsByText(text)) {
            throw new DuplicateException(DUPLICATE_SAMPLE_TEXT);
        }
    }
    private Sample saveText(String text) {
        Sample sample = Sample.createSample(text);
        sampleRepository.save(sample);
        return sample;
    }
}
