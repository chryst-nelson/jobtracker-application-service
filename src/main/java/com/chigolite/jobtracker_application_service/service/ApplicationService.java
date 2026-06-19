package com.chigolite.jobtracker_application_service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chigolite.jobtracker_application_service.common.exceptionHandler.ResourceNotFound;
import com.chigolite.jobtracker_application_service.dto.ApplicationRequest;
import com.chigolite.jobtracker_application_service.dto.ApplicationResponse;
import com.chigolite.jobtracker_application_service.dto.ApplicationStats;
import com.chigolite.jobtracker_application_service.dto.StatusUpdateRequest;
import com.chigolite.jobtracker_application_service.entity.Application;
import com.chigolite.jobtracker_application_service.entity.ApplicationStatus;
import com.chigolite.jobtracker_application_service.repository.ApplicationRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepo applicationRepository;

    public ApplicationResponse create(ApplicationRequest request, Long userId) {
        Application app = new Application();
        app.setUserId(userId);
        app.setCompanyName(request.getCompanyName());
        app.setJobTitle(request.getJobTitle());
        app.setJobUrl(request.getJobUrl());
        app.setSalaryExpectation(request.getSalaryExpectation());
        app.setStatus(request.getStatus() != null ? request.getStatus() : ApplicationStatus.APPLIED);
        app.setAppliedDate(request.getAppliedDate());
        app.setDeadline(request.getDeadline());
        app.setNotes(request.getNotes());

        return mapToResponse(applicationRepository.save(app));
    }

    public Page<ApplicationResponse> getAll(Long userId, Pageable pageable) {
        return applicationRepository.findByUserId(userId, pageable)
                .map(this::mapToResponse);
    }

    public ApplicationResponse getById(Long id, Long userId) {
        Application app = applicationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFound("Application not found"));
        return mapToResponse(app);
    }

    public ApplicationResponse update(Long id, ApplicationRequest request, Long userId) {
        Application app = applicationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFound("Application not found"));

        app.setCompanyName(request.getCompanyName());
        app.setJobTitle(request.getJobTitle());
        app.setJobUrl(request.getJobUrl());
        app.setSalaryExpectation(request.getSalaryExpectation());
        app.setAppliedDate(request.getAppliedDate());
        app.setDeadline(request.getDeadline());
        app.setNotes(request.getNotes());

        return mapToResponse(applicationRepository.save(app));
    }

    public ApplicationResponse updateStatus(Long id,
            StatusUpdateRequest request, Long userId) {
        Application app = applicationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFound("Application not found"));
        app.setStatus(request.getStatus());
        return mapToResponse(applicationRepository.save(app));
    }

    public void delete(Long id, Long userId) {
        Application app = applicationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFound("Application not found"));
        applicationRepository.delete(app);
    }

    public ApplicationStats getStats(Long userId) {
        long total = applicationRepository.countByUserId(userId);

        List<Object[]> counts = applicationRepository.countByUserIdGroupByStatus(userId);

        // Build a map of status -> count from the query results
        Map<ApplicationStatus, Long> statusMap = new HashMap<>();
        for (Object[] row : counts) {
            ApplicationStatus status = (ApplicationStatus) row[0];
            Long count = (Long) row[1];
            statusMap.put(status, count);
        }

        return new ApplicationStats(
                total,
                statusMap.getOrDefault(ApplicationStatus.APPLIED, 0L),
                statusMap.getOrDefault(ApplicationStatus.INTERVIEW_SCHEDULED, 0L),
                statusMap.getOrDefault(ApplicationStatus.INTERVIEWED, 0L),
                statusMap.getOrDefault(ApplicationStatus.OFFER_RECEIVED, 0L),
                statusMap.getOrDefault(ApplicationStatus.ACCEPTED, 0L),
                statusMap.getOrDefault(ApplicationStatus.REJECTED, 0L),
                statusMap.getOrDefault(ApplicationStatus.WITHDRAWN, 0L));
    }

    private ApplicationResponse mapToResponse(Application app) {
        return new ApplicationResponse(
                app.getId(),
                app.getUserId(),
                app.getCompanyName(),
                app.getJobTitle(),
                app.getJobUrl(),
                app.getSalaryExpectation(),
                app.getStatus(),
                app.getAppliedDate(),
                app.getDeadline(),
                app.getNotes(),
                app.getCreatedAt(),
                app.getUpdatedAt());
    }
}
