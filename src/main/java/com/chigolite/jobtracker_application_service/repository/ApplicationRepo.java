package com.chigolite.jobtracker_application_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chigolite.jobtracker_application_service.entity.Application;
import com.chigolite.jobtracker_application_service.entity.ApplicationStatus;

@Repository
public interface ApplicationRepo
        extends JpaRepository<Application, Long> {

    Page<Application> findByUserId(Long userId, Pageable pageable);

    List<Application> findByUserId(Long userId);

    Optional<Application> findByIdAndUserId(Long id, Long userId);

    long countByUserIdAndStatus(Long userId, ApplicationStatus status);
}
