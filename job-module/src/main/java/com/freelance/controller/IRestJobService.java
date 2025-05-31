package com.freelance.controller;

import com.freelance.dto.JobContentRequest;
import com.freelance.dto.JobContentResponse;
import com.freelance.dto.JobPostingRequest;
import com.freelance.dto.JobPostingResponse;

public interface IRestJobService {

    public RootEntity<JobPostingResponse> saveJobPosting(JobPostingRequest request);
}
