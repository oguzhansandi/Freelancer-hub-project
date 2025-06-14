package com.freelance.services;

import com.freelance.dto.user.DtoUser;
import com.freelance.model.auth.AuthRequest;
import com.freelance.model.auth.AuthResponse;

public interface IAuthenticationService {

    public DtoUser register(AuthRequest input);

    public AuthResponse authenticate(AuthRequest input);

    public void logout();

    public DtoUser getProfile();

    public void deleteProfile();
}
