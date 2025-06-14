package com.freelance.service;

import com.freelance.dto.*;
import com.freelance.dto.job.JobFilterRequest;

import java.util.List;

public interface IJobService {

    public JobPostingResponse jobPost(JobPostingRequest request);

    public List<JobListingResponse> getJobs(JobFilterRequest filter);

    public JobPostingResponse updateJob(JobPostingRequest request, Long id);

    public String deleteJob(Long id);
}
