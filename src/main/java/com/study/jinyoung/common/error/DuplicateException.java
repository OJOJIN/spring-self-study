package com.study.jinyoung.common.error;

public class DuplicateException extends ApplicationException{

    public DuplicateException(ApplicationError error) {
        super(error);
    }
}
