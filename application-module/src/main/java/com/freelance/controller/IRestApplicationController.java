package com.freelance.controller;

import com.freelance.dto.ApplicationReplyRequest;
import com.freelance.dto.ApplicationRequest;
import com.freelance.dto.ApplicationResponse;

import java.util.List;

public interface IRestApplicationController {

    public RootEntity<ApplicationResponse> applyToJob(ApplicationRequest request);

    public RootEntity<List<ApplicationResponse>> getMyApplications();

    public RootEntity<List<ApplicationResponse>> getApplicationsForEmployer();

    public RootEntity<ApplicationResponse> applicationReply(Long id, ApplicationReplyRequest request);

}
