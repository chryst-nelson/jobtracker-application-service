package com.chigolite.jobtracker_application_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chigolite.jobtracker_application_service.entity.Application;

@Repository
public interface ApplicationRepo
        extends JpaRepository<Application, Long> {

    Page<Application> findByUserId(Long userId, Pageable pageable);

    List<Application> findByUserId(Long userId);

    Optional<Application> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT a.status, COUNT(a) FROM Application a WHERE a.userId = :userId GROUP BY a.status")
    List<Object[]> countByUserIdGroupByStatus(@Param("userId") Long userId);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.userId = :userId")
    long countByUserId(@Param("userId") Long userId);
}
