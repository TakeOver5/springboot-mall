package com.mick.springbootmall.service.impl;

import com.mick.springbootmall.dao.UserDao;
import com.mick.springbootmall.dto.UserRegisterRequest;
import com.mick.springbootmall.model.User;
import com.mick.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
