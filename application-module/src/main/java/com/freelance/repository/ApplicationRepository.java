package com.freelance.repository;

import com.freelance.model.Application;
import com.freelance.model.job.JobPosting;
import com.freelance.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByJobPostingAndFreelancer(JobPosting jobPosting, User freelancer);
}
