package com.freelance.controller.impl;

import com.freelance.controller.IRestJobService;
import com.freelance.controller.RootEntity;
import com.freelance.dto.JobContentRequest;
import com.freelance.dto.JobContentResponse;
import com.freelance.dto.JobPostingRequest;
import com.freelance.dto.JobPostingResponse;
import com.freelance.model.BaseEntity;
import com.freelance.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/jobs")
public class RestJobService extends BaseEntity implements IRestJobService {

    @Autowired
    private IJobService jobService;

    @Override
    @PostMapping
    public RootEntity<JobPostingResponse> saveJobPosting(@RequestBody JobPostingRequest request) {
        return RootEntity.ok(jobService.jobPost(request));
    }
}
