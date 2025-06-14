package com.freelance.controller;

import com.freelance.dto.ApplicationRequest;
import com.freelance.dto.ApplicationResponse;

public interface IRestApplicationController {

    public RootEntity<ApplicationResponse> applyToJob(ApplicationRequest request);
}
