package com.chigolite.jobtracker_application_service.service;

import java.util.List;

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
        List<Application> all = applicationRepository.findByUserId(userId);
        return new ApplicationStats(
                all.size(),
                countByStatus(all, ApplicationStatus.APPLIED),
                countByStatus(all, ApplicationStatus.INTERVIEW_SCHEDULED),
                countByStatus(all, ApplicationStatus.INTERVIEWED),
                countByStatus(all, ApplicationStatus.OFFER_RECEIVED),
                countByStatus(all, ApplicationStatus.ACCEPTED),
                countByStatus(all, ApplicationStatus.REJECTED),
                countByStatus(all, ApplicationStatus.WITHDRAWN));
    }

    private long countByStatus(List<Application> apps, ApplicationStatus status) {
        return apps.stream().filter(a -> a.getStatus() == status).count();
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
