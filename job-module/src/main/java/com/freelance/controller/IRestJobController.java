package com.freelance.controller;

import com.freelance.dto.JobFilterRequest;
import com.freelance.dto.JobListingResponse;
import com.freelance.dto.JobPostingRequest;
import com.freelance.dto.JobPostingResponse;
import com.freelance.model.JobContent;
import com.freelance.model.JobPosting;

import java.util.List;

public interface IRestJobController {

    public RootEntity<JobPostingResponse> saveJobPosting(JobPostingRequest request);

    public RootEntity<List<JobListingResponse>> getJobs(JobFilterRequest filter);

}
