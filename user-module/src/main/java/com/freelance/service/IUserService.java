package com.freelance.service;

import com.freelance.dto.DtoUser;
import com.freelance.model.UpdateUserRequest;
import com.freelance.model.UserResponse;

public interface IUserService {

    public DtoUser getUser();

    public UserResponse updateUser(UpdateUserRequest request);

}
