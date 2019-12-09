package com.selenium.sdjubbs.common.controller;

import com.selenium.sdjubbs.common.api.Api;
import com.selenium.sdjubbs.common.bean.User;
import com.selenium.sdjubbs.common.service.UserService;
import com.selenium.sdjubbs.common.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@io.swagger.annotations.Api(value = "SDJUBBS Admin API", tags = "SDJUBBS Admin API")
public class AdminApiController {
    @Autowired
    private UserService userService;

    @GetMapping(Api.USER)
    @ApiOperation(value = "获取所有的用户")
    public Result getAllUser() {
        List<User> users = userService.getAllUser();
        if (users == null) {
            return Result.failure("暂时没有用户");
        }
        return Result.success().add("users", users);
    }
}
