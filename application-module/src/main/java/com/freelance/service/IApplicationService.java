package com.freelance.service;

import com.freelance.dto.ApplicationRequest;
import com.freelance.dto.ApplicationResponse;

public interface IApplicationService {

    public ApplicationResponse applyToJob(ApplicationRequest request);

}
