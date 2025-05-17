package com.freelance.controller;

import com.freelance.dto.DtoUser;
import com.freelance.model.AuthRequest;
import com.freelance.model.AuthResponse;

public interface IRestAuthenticationController {

    public RootEntity<DtoUser> register(AuthRequest input);

    public RootEntity<AuthResponse> authenticate(AuthRequest input);

    public RootEntity<String> logout();

    public RootEntity<DtoUser> getProfile();
}
