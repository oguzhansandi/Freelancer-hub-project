package com.freelance.controller.impl;

import com.freelance.controller.IRestAuthenticationController;
import com.freelance.controller.RootEntity;
import com.freelance.dto.DtoUser;
import com.freelance.model.AuthRequest;
import com.freelance.model.AuthResponse;
import com.freelance.model.BaseEntity;
import com.freelance.services.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthenticationController implements IRestAuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @Override
    @PostMapping("/register")
    public RootEntity<DtoUser> register(@RequestBody AuthRequest input) {
        return RootEntity.ok(authenticationService.register(input));
    }

    @Override
    @PostMapping("/authenticate")
    public RootEntity<AuthResponse> authenticate(@RequestBody AuthRequest input) {
        return RootEntity.ok(authenticationService.authenticate(input));
    }
}
