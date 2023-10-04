package com.study.jinyoung.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@RequiredArgsConstructor
public enum ApplicationError {

    /**
     *  400 Bad Request
     */


    /**
     *  401 Unauthorized
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근할 수 있는 권한이 없습니다."),


    /**
     *  403 Forbidden
     */
    INVAILID_JWT_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,  "유효하지 JWT ACCESS 토큰 입니다."),


    /**
     *  404 Not Found
     */


    /**
     * 409 Conflict
     */
    DUPLICATE_SAMPLE_TEXT(HttpStatus.CONFLICT, "이미 존재하는 TEXT입니다."),


    /**
     *  500 INTERNAL SERVER ERROR
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");


    private final HttpStatus status;
    private final String message;
}
