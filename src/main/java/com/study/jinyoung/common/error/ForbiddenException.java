package com.study.jinyoung.common.error;

public class ForbiddenException extends ApplicationException{
    public ForbiddenException(ErrorCode error) {
        super(error);
    }
}
