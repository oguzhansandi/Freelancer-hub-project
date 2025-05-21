package com.freelance.controller;

import com.freelance.dto.DtoUser;
import com.freelance.model.UpdateUserContactRequest;
import com.freelance.model.UpdateUserRequest;
import com.freelance.model.UserContactResponse;
import com.freelance.model.UserResponse;

public interface IRestUserController {

    public RootEntity<DtoUser> getUser();

    public RootEntity<UserResponse> updateUser(UpdateUserRequest request);

    public RootEntity<UserContactResponse> updateUserContact(UpdateUserContactRequest request);
}
