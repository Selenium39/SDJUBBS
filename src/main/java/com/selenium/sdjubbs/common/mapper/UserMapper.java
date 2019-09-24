package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    Integer addUser(User user);

    User getUserByUsername(String username);
    User getUserByEmail(String email);
}
