package com.chigolite.jobtracker_application_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.chigolite.jobtracker_application_service.entity.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {
    private Long id;
    private Long userId;
    private String companyName;
    private String jobTitle;
    private String jobUrl;
    private BigDecimal salaryExpectation;
    private ApplicationStatus status;
    private LocalDate appliedDate;
    private LocalDate deadline;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
