package com.mick.springbootmall.service;

import com.mick.springbootmall.dto.UserLoginRequest;
import com.mick.springbootmall.dto.UserRegisterRequest;
import com.mick.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);
}
