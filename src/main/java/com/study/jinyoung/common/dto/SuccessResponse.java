package com.study.jinyoung.common.dto;

import com.study.jinyoung.common.dto.code.SuccessCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SuccessResponse<T> {
    private final int status;
    private final String message;
    private T data;

    public static ResponseEntity<SuccessResponse> of(SuccessCode success) {
        return ResponseEntity.status(success.getStatus())
                .body(new SuccessResponse(success.getStatusCode(), success.getMessage()));
    }


    public static <T> ResponseEntity<SuccessResponse<T>> of(SuccessCode success, T data) {
        return ResponseEntity.status(success.getStatus())
                .body(new SuccessResponse<T>(success.getStatusCode(), success.getMessage(), data));
    }
}
