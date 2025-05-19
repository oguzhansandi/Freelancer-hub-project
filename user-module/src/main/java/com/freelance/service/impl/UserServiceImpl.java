package com.freelance.service.impl;

import com.freelance.dto.DtoUser;
import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.model.UpdateUserRequest;
import com.freelance.model.User;
import com.freelance.model.UserResponse;
import com.freelance.repository.UserRepository;
import com.freelance.service.IUserService;
import com.freelance.services.CommonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DtoUser getUser() {
        DtoUser dtoUser = new DtoUser();

        String username = commonService.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, "username : " + username)
                ));

        BeanUtils.copyProperties(user,  dtoUser);
        return dtoUser;
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request) {
       try {
           String username = commonService.getCurrentUsername();

           User user = userRepository.findByUsername(username)
                   .orElseThrow(() -> new BaseException(
                           new ErrorMessage(MessageType.USER_NOT_FOUND, "username : " + request.getUsername())
                   ));
           modelMapper.map(request, user);
           User savedUser = userRepository.save(user);

           return modelMapper.map(savedUser, UserResponse.class);

       }catch (Exception e){
           throw new BaseException(new ErrorMessage(MessageType.UPDATE_FAILED, e.getMessage()));
       }
   }
}
