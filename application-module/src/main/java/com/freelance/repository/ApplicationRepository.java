package com.freelance.repository;

import com.freelance.model.Application;
import com.freelance.model.job.JobPosting;
import com.freelance.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByJobPostingAndFreelancer(JobPosting jobPosting, User freelancer);

    List<Application> findByFreelancer(User freelancer);

    List<Application> findByJobPostingEmployer(User employer);
}
