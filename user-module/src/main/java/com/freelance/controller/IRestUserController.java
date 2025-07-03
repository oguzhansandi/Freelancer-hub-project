package com.freelance.controller;

import com.freelance.dto.user.DtoUser;
import com.freelance.model.UpdateUserContactRequest;
import com.freelance.model.UpdateUserRequest;
import com.freelance.model.UserContactResponse;
import com.freelance.model.UserResponse;

import java.security.Principal;
import java.util.List;

public interface IRestUserController {

    public RootEntity<DtoUser> getUser();

    public RootEntity<UserResponse> updateUser(UpdateUserRequest request);

    public RootEntity<UserContactResponse> updateUserContact(UpdateUserContactRequest request);

    public RootEntity<List<String>> getAllUsers(Principal principal);
}
