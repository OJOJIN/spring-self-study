package com.study.jinyoung.common.error;

public class UnauthorizedException extends ApplicationException{
    public UnauthorizedException(ApplicationError error) {
        super(error);
    }
}
