package com.chigolite.jobtracker_application_service.common.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

    private boolean success;
    private T data;

}
