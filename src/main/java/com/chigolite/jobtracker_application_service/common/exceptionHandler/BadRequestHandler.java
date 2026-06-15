package com.chigolite.jobtracker_application_service.common.exceptionHandler;

public class BadRequestHandler extends AppExceptionHandler {

    public BadRequestHandler(String message) {
        super(message, "BAD_REQUEST");
    }
}
