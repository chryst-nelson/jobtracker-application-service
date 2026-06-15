package com.chigolite.jobtracker_application_service.dto;

import com.chigolite.jobtracker_application_service.entity.ApplicationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateRequest {
    @NotNull(message = "Status is required")
    private ApplicationStatus status;
}
