package com.freelance.service;

import com.freelance.dto.*;
import com.freelance.model.JobPosting;
import com.freelance.model.ServicePackage;

import java.util.List;

public interface IJobService {

    public JobPostingResponse jobPost(JobPostingRequest request);
}
