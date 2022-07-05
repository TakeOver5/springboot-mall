package com.mick.springbootmall.service.impl;

import com.mick.springbootmall.dao.UserDao;
import com.mick.springbootmall.dto.UserLoginRequest;
import com.mick.springbootmall.dto.UserRegisterRequest;
import com.mick.springbootmall.model.User;
import com.mick.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;

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

        // MD5 生成
        // 要將字串轉成 byte 類型
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());

        // 設定 hash 過後的雜湊值
        userRegisterRequest.setPassword(hashedPassword);

        // 創建
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        // 檢測帳密是否完全一致
        // 如果信箱存在，接著檢測密碼，不存在是 null
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());


        // 檢查 user 是否存在
        if(user == null) {
            // 沒有被註冊過
            log.warn("該 email {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        // 比較密碼
        if(user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            log.warn("email {} 的密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
