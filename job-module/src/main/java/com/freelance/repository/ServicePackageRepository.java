package com.freelance.repository;

import com.freelance.model.JobPosting;
import com.freelance.model.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicePackageRepository extends JpaRepository<ServicePackage,Long> {
    List<ServicePackage> findByJobPostingId(Long jobPostingId);

    @Query("SELECT p FROM ServicePackage p LEFT JOIN FETCH p.features f LEFT JOIN FETCH f.featureDefinition WHERE p.jobPosting.id = :jobId")
    List<ServicePackage> findByJobPostingIdWithFeatures(@Param("jobId") Long jobId);
}
