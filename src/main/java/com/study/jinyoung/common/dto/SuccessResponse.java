package com.study.jinyoung.common.dto;

import com.study.jinyoung.common.dto.code.SuccessCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SuccessResponse<T> {
    private final int status;
    private final String message;
    private T data;

    public static SuccessResponse success(SuccessCode success) {
        return new SuccessResponse<>(success.getStatusCode(), success.getMessage());
    }


    public static <T> SuccessResponse<T> success(SuccessCode success, T data) {
        return new SuccessResponse<T>(success.getStatusCode(), success.getMessage(), data);
    }
}
