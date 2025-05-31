package com.freelance.repository;

import com.freelance.model.JobPosting;
import com.freelance.model.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicePackageRepository extends JpaRepository<ServicePackage,Long> {
    List<ServicePackage> findByJobPostingId(Long jobPostingId);
}
