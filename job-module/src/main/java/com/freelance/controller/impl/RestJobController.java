package com.freelance.controller.impl;

import com.freelance.controller.IRestJobController;
import com.freelance.controller.RootEntity;
import com.freelance.dto.job.JobFilterRequest;
import com.freelance.dto.JobListingResponse;
import com.freelance.dto.JobPostingRequest;
import com.freelance.dto.JobPostingResponse;
import com.freelance.model.common.BaseEntity;
import com.freelance.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/jobs")
public class RestJobController extends BaseEntity implements IRestJobController {

    @Autowired
    private IJobService jobService;

    @Override
    @PostMapping
    public RootEntity<JobPostingResponse> saveJobPosting(@RequestBody JobPostingRequest request) {
        return RootEntity.ok(jobService.jobPost(request));
    }

    @Override
    @GetMapping
    public RootEntity<List<JobListingResponse>> getJobs(@RequestBody JobFilterRequest filter) {
        return RootEntity.ok(jobService.getJobs(filter));
    }

    @Override
    @PutMapping("/{id}")
    public RootEntity<JobPostingResponse> updateJob(
            @RequestBody JobPostingRequest request,
            @PathVariable("id") Long id) {
        return RootEntity.ok(jobService.updateJob(request, id));
    }

    @Override
    @DeleteMapping("/{id}")
    public RootEntity<String> deleteJob(@PathVariable("id") Long id) {
        jobService.deleteJob(id);
        return RootEntity.ok("iş ilanı silindi");
    }
}
