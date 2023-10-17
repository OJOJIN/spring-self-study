package com.study.jinyoung.common.error;

public class EntityNotFoundException extends ApplicationException{
    public EntityNotFoundException(ErrorCode error) {
        super(error);
    }
}
