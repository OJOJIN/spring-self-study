package com.study.jinyoung.common.error;

public class UnauthorizedException extends ApplicationException{
    public UnauthorizedException(ErrorCode error) {
        super(error);
    }
}
