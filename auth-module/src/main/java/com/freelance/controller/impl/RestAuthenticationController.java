package com.freelance.controller.impl;

import com.freelance.controller.IRestAuthenticationController;
import com.freelance.controller.RootEntity;
import com.freelance.dto.user.DtoUser;
import com.freelance.model.auth.AuthRequest;
import com.freelance.model.auth.AuthResponse;
import com.freelance.services.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestAuthenticationController implements IRestAuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/register")
    @Override
    public RootEntity<DtoUser> register(@RequestBody AuthRequest input) {
        return RootEntity.ok(authenticationService.register(input));
    }

    @PostMapping("/authenticate")
    @Override
    public RootEntity<AuthResponse> authenticate(@RequestBody AuthRequest input) {
        return RootEntity.ok(authenticationService.authenticate(input));
    }

    @Override
    @PostMapping("/logout")
    public RootEntity<String> logout() {
        authenticationService.logout();
        return RootEntity.ok("Çıkış yapıldı");

    }

    @Override
    @GetMapping("/me")
    public RootEntity<DtoUser> getProfile() {
        return RootEntity.ok(authenticationService.getProfile());
    }

    @Override
    @DeleteMapping("/delete_me")
    public RootEntity<?> deleteProfile() {
        authenticationService.deleteProfile();
        return RootEntity.ok("Kullanıcı başarıyla silindi.");
    }
}
