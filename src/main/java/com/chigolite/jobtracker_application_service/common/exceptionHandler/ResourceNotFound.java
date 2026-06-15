package com.chigolite.jobtracker_application_service.common.exceptionHandler;

public class ResourceNotFound extends AppExceptionHandler {

    public ResourceNotFound(String message) {
        super(message, "RESOURCE_NOT_FOUND");
    }
}
