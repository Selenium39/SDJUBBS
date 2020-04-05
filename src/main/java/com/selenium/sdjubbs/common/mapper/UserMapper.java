package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.LoginUserInfo;
import com.selenium.sdjubbs.common.bean.RegisterUserInfo;
import com.selenium.sdjubbs.common.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    Integer addUser(User user);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserById(Integer id);

    Integer updateUser(User user);

    Integer deleteUser(Integer id);

    Integer deleteUserByBatch(List<Integer> ids);

    List<User> getAllUser();

    List<User> getAllUserBySearch(String search);

    List<RegisterUserInfo> getUserOrderByRegisterTime(int num);

    List<LoginUserInfo> getUserOrderByLoginTime(int num);

    Integer getUserCount();

}
