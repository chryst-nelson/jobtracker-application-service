package com.chigolite.jobtracker_application_service.common.exceptionHandler;

public class DuplicationException extends AppExceptionHandler {

    public DuplicationException(String message) {

        super(message, "CONFLICT");
    }

}
