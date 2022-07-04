package com.mick.springbootmall.service.impl;

import com.mick.springbootmall.dao.UserDao;
import com.mick.springbootmall.dto.UserRegisterRequest;
import com.mick.springbootmall.model.User;
import com.mick.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 嘗試使用前端傳過來的資料
        // 檢查 email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if(user != null) {
            log.warn("該 email {} 己經被註冊了", userRegisterRequest.getEmail());
            // 表示註冊過了 400 參數有問題
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return userDao.createUser(userRegisterRequest);
    }
}
