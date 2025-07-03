package com.freelance.service;

import com.freelance.dto.ApplicationReplyRequest;
import com.freelance.dto.ApplicationRequest;
import com.freelance.dto.ApplicationResponse;

import java.util.List;

public interface IApplicationService {

    public ApplicationResponse applyToJob(ApplicationRequest request);

    public List<ApplicationResponse> getMyApplications();

    public List<ApplicationResponse> getApplicationsForEmployer();

    public ApplicationResponse applicationReply(Long id, ApplicationReplyRequest request);


}
