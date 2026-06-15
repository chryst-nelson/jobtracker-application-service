package com.chigolite.jobtracker_application_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.chigolite.jobtracker_application_service.entity.ApplicationStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {
    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    private String jobUrl;
    private BigDecimal salaryExpectation;
    private ApplicationStatus status;

    @NotNull(message = "Applied date is required")
    private LocalDate appliedDate;

    private LocalDate deadline;
    private String notes;
}
