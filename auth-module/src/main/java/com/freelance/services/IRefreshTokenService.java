package com.freelance.services;

import com.freelance.jwt.RefreshTokenRequest;
import com.freelance.model.AuthResponse;

public interface IRefreshTokenService {

    public AuthResponse refreshToken(RefreshTokenRequest request);

}
