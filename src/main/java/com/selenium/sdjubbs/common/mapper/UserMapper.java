package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    Integer addUser(User user);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    List<User> getAllUser();
}
