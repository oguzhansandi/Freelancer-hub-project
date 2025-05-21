package com.freelance.controller.impl;

import com.freelance.controller.IRestUserController;
import com.freelance.controller.RootEntity;
import com.freelance.dto.DtoUser;
import com.freelance.model.*;
import com.freelance.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/user")
public class RestUserControllerImpl extends BaseEntity implements IRestUserController {

    @Autowired
    private IUserService userService;

    @Override
    @GetMapping
    public RootEntity<DtoUser> getUser() {
        return RootEntity.ok(userService.getUser());
    }

    @Override
    @PutMapping
    public RootEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request) {
        return RootEntity.ok(userService.updateUser(request));
    }

    @Override
    @PutMapping("/contact")
    public RootEntity<UserContactResponse> updateUserContact(@RequestBody UpdateUserContactRequest request) {
        return RootEntity.ok(userService.updateUserContact(request));
    }
}
