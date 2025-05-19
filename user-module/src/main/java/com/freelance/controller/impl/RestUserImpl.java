package com.freelance.controller.impl;

import com.freelance.controller.IRestUserService;
import com.freelance.controller.RootEntity;
import com.freelance.dto.DtoUser;
import com.freelance.model.BaseEntity;
import com.freelance.model.UpdateUserRequest;
import com.freelance.model.UserResponse;
import com.freelance.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/user")
public class RestUserImpl extends BaseEntity implements IRestUserService {

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
}
