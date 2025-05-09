package com.freelance.services;

import com.freelance.dto.DtoUser;
import com.freelance.model.AuthRequest;

public interface IAuthenticationService {

    public DtoUser register(AuthRequest input);
}
