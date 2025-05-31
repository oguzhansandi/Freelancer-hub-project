package com.freelance.repository;

import com.freelance.model.JobContent;
import com.freelance.model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobContentRepository  extends JpaRepository<JobContent, Long> {
    Optional<JobContent> findByJobPosting(JobPosting jobPosting);

}
