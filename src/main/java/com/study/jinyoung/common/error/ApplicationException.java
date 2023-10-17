package com.study.jinyoung.common.error;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorCode error;

    public ApplicationException(ErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }
}
