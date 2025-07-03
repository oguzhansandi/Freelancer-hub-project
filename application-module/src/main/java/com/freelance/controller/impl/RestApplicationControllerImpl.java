package com.freelance.controller.impl;

import com.freelance.controller.IRestApplicationController;
import com.freelance.controller.RootEntity;
import com.freelance.dto.ApplicationReplyRequest;
import com.freelance.dto.ApplicationRequest;
import com.freelance.dto.ApplicationResponse;
import com.freelance.service.IApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/applications")
public class RestApplicationControllerImpl implements IRestApplicationController {

    @Autowired
    private IApplicationService applicationService;

    @Override
    @PostMapping
    public RootEntity<ApplicationResponse> applyToJob(@RequestBody ApplicationRequest request)
    {
        return RootEntity.ok(applicationService.applyToJob(request));
    }

    @Override
    @GetMapping("/my-applications")
    public RootEntity<List<ApplicationResponse>> getMyApplications() {
        return RootEntity.ok(applicationService.getMyApplications());
    }

    @Override
    @GetMapping("/my-job-applications")
    public RootEntity<List<ApplicationResponse>> getApplicationsForEmployer() {
        return RootEntity.ok(applicationService.getApplicationsForEmployer());
    }

    @Override
    @PutMapping("/{id}/reply")
    public RootEntity<ApplicationResponse> applicationReply(
            @PathVariable("id") Long id,
            @RequestBody ApplicationReplyRequest request
    ) {
        return RootEntity.ok(applicationService.applicationReply(id, request));
    }
}
