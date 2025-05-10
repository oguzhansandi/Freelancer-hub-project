package com.freelance.services;

import com.freelance.dto.DtoUser;
import com.freelance.model.AuthRequest;
import com.freelance.model.AuthResponse;

public interface IAuthenticationService {

    public DtoUser register(AuthRequest input);

}
