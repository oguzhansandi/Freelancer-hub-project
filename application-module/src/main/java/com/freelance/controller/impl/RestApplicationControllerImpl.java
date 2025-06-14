package com.freelance.controller.impl;

import com.freelance.controller.IRestApplicationController;
import com.freelance.controller.RootEntity;
import com.freelance.dto.ApplicationRequest;
import com.freelance.dto.ApplicationResponse;
import com.freelance.service.IApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
