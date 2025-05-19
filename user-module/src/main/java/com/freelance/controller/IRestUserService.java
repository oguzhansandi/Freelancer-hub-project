package com.freelance.controller;

import com.freelance.dto.DtoUser;
import com.freelance.model.UpdateUserRequest;
import com.freelance.model.UserResponse;

public interface IRestUserService {

    public RootEntity<DtoUser> getUser();

    public RootEntity<UserResponse> updateUser(UpdateUserRequest request);

}
