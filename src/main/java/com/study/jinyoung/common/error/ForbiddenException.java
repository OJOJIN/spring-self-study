package com.study.jinyoung.common.error;

public class ForbiddenException extends ApplicationException{
    public ForbiddenException(ApplicationError error) {
        super(error);
    }
}
