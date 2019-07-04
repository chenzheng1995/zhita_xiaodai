package com.zhita.controller.operator.exception;

public class Oauth2TokenException extends RuntimeException {

    public Oauth2TokenException() {
        super();
    }

    public Oauth2TokenException(String message) {
        super(message);
    }

    public Oauth2TokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public Oauth2TokenException(Throwable cause) {
        super(cause);
    }

    protected Oauth2TokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
