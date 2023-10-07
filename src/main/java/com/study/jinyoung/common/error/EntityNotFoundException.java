package com.study.jinyoung.common.error;

public class EntityNotFoundException extends ApplicationException{
    public EntityNotFoundException(ApplicationError error) {
        super(error);
    }
}
