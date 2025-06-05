package com.freelance.repository;

import com.freelance.model.JobContent;
import com.freelance.model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JobContentRepository  extends JpaRepository<JobContent, Long>, JpaSpecificationExecutor<JobContent> {
    Optional<JobContent> findByJobPosting(JobPosting jobPosting);

}
