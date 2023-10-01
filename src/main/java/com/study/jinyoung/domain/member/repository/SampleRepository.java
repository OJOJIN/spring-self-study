package com.study.jinyoung.domain.member.repository;

import com.study.jinyoung.domain.member.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long> {
    Boolean existsByText(String text);
}
