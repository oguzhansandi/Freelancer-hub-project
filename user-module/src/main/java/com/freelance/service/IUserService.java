package com.freelance.service;

import com.freelance.dto.user.DtoUser;
import com.freelance.model.UpdateUserContactRequest;
import com.freelance.model.UpdateUserRequest;
import com.freelance.model.UserContactResponse;
import com.freelance.model.UserResponse;

public interface IUserService {

    public DtoUser getUser();

    public UserResponse updateUser(UpdateUserRequest request);

    public UserContactResponse updateUserContact(UpdateUserContactRequest request);
}
