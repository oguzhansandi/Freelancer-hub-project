package com.freelance.model.auth;

import com.freelance.model.user.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    private String username;

    private String mail;

    private String password;

    private String firstName;

    private String lastName;

    private Role role;
}
