package com.freelance.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String username;

    private String accessToken;

    private String refreshToken;
}
