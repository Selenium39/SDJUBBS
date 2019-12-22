package com.selenium.sdjubbs.common.service;

import com.selenium.sdjubbs.common.bean.User;
import com.selenium.sdjubbs.common.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }


}
