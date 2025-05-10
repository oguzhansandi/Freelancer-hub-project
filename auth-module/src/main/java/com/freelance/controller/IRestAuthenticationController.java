package com.freelance.controller;

import com.freelance.dto.DtoUser;
import com.freelance.model.AuthRequest;

public interface IRestAuthenticationController {

    public RootEntity<DtoUser> register(AuthRequest input);
}
