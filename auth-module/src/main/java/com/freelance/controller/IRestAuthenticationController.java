package com.freelance.controller;

import com.freelance.dto.user.DtoUser;
import com.freelance.model.auth.AuthRequest;
import com.freelance.model.auth.AuthResponse;

public interface IRestAuthenticationController {

    public RootEntity<DtoUser> register(AuthRequest input);

    public RootEntity<AuthResponse> authenticate(AuthRequest input);

    public RootEntity<String> logout();

    public RootEntity<DtoUser> getProfile();

    public RootEntity<?> deleteProfile();
}
