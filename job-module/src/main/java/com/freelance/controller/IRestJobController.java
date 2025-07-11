package com.freelance.controller;

import com.freelance.dto.job.JobFilterRequest;
import com.freelance.dto.JobListingResponse;
import com.freelance.dto.JobPostingRequest;
import com.freelance.dto.JobPostingResponse;

import java.util.List;

public interface IRestJobController {

    public RootEntity<JobPostingResponse> saveJobPosting(JobPostingRequest request);

    public RootEntity<List<JobListingResponse>> getJobs(JobFilterRequest filter);

    public RootEntity<JobPostingResponse> updateJob(JobPostingRequest request, Long id);

    public RootEntity<String> deleteJob(Long id);
}
