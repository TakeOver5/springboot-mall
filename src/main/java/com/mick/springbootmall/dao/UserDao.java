package com.mick.springbootmall.dao;

import com.mick.springbootmall.dto.UserRegisterRequest;
import com.mick.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);
    Integer createUser(UserRegisterRequest userRegisterRequest);

}
