package com.study.jinyoung.domain.sample.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Sample {

    @Id
    @Column(name = "sample_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    public static Sample createSample(String text) {
        return Sample.builder()
                .text(text)
                .build();
    }
}
