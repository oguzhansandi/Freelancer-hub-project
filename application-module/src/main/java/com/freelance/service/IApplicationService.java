package com.freelance.service;

import com.freelance.controller.RootEntity;
import com.freelance.dto.ApplicationRequest;
import com.freelance.dto.ApplicationResponse;

import java.util.List;

public interface IApplicationService {

    public ApplicationResponse applyToJob(ApplicationRequest request);

    public List<ApplicationResponse> getMyApplications();

    public List<ApplicationResponse> getApplicationsForEmployer();


}
