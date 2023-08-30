package com.blog.app.apis.exception;

public class ApiException extends  RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException() {
        super();
    }
}
