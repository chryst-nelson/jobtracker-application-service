package com.chigolite.jobtracker_application_service.common.exceptionHandler;

import lombok.Getter;

@Getter
public class AppExceptionHandler extends RuntimeException {

    private final String errorCode;

    public AppExceptionHandler(
            String message,
            String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
