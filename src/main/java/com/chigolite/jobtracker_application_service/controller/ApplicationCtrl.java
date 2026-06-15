package com.chigolite.jobtracker_application_service.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chigolite.jobtracker_application_service.common.util.ApiResponse;
import com.chigolite.jobtracker_application_service.dto.ApplicationRequest;
import com.chigolite.jobtracker_application_service.dto.ApplicationResponse;
import com.chigolite.jobtracker_application_service.dto.ApplicationStats;
import com.chigolite.jobtracker_application_service.dto.StatusUpdateRequest;
import com.chigolite.jobtracker_application_service.service.ApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationCtrl {

    private final ApplicationService applicationService;

    private Long getCurrentUserId() {
        String userId = (String) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return Long.parseLong(userId);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApplicationResponse>> create(
            @RequestBody @Valid ApplicationRequest request) {

        return ResponseEntity.ok(ApiResponse.<ApplicationResponse>builder()
                .success(true)
                .data(applicationService.create(request, getCurrentUserId()))
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ApplicationResponse>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.<Page<ApplicationResponse>>builder()
                .success(true)
                .data(applicationService.getAll(getCurrentUserId(), pageable))
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplicationResponse>> getById(@PathVariable Long id) {

        return ResponseEntity.ok(ApiResponse.<ApplicationResponse>builder()
                .success(true)
                .data(applicationService.getById(id, getCurrentUserId()))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplicationResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid ApplicationRequest request) {

        return ResponseEntity.ok(ApiResponse.<ApplicationResponse>builder()
                .success(true)
                .data(applicationService.update(id, request, getCurrentUserId()))
                .build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ApplicationResponse>> updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid StatusUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.<ApplicationResponse>builder()
                .success(true)
                .data(applicationService.updateStatus(id, request, getCurrentUserId()))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        applicationService.delete(id, getCurrentUserId());
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .build());
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<ApplicationStats>> getStats() {
        return ResponseEntity.ok(ApiResponse.<ApplicationStats>builder()
                .success(true)
                .data(applicationService.getStats(getCurrentUserId()))
                .build());
    }
}
