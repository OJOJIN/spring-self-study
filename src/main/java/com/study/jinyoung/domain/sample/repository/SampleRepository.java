package com.study.jinyoung.domain.sample.repository;

import com.study.jinyoung.domain.sample.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long> {
    Boolean existsByText(String text);
}
