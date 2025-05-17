package com.freelance.services.impl;

import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.jwt.JWTService;
import com.freelance.jwt.RefreshTokenRequest;
import com.freelance.model.AuthResponse;
import com.freelance.model.RefreshToken;
import com.freelance.model.User;
import com.freelance.repository.RefreshTokenRepository;
import com.freelance.services.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class RefreshTokenServiceImpl implements IRefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JWTService jwtService;

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest input) {
        try {
            RefreshToken token = refreshTokenRepository.findByRefreshToken(input.getRefreshToken())
                    .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND, "Token not found")));

            if (token.getExpiredDate().before(new Date())) {
                throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_EXPIRED, "Token expired"));
            }

            User user = token.getUser();
            String newAccessToken = jwtService.generateToken(user);

            return new AuthResponse(user.getUsername(), newAccessToken, token.getRefreshToken());
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_FAILED, e.getMessage()));
        }
    }

}
