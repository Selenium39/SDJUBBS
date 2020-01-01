package com.selenium.sdjubbs.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selenium.sdjubbs.common.api.Api;
import com.selenium.sdjubbs.common.bean.User;
import com.selenium.sdjubbs.common.config.SdjubbsSetting;
import com.selenium.sdjubbs.common.service.UserService;
import com.selenium.sdjubbs.common.util.Constant;
import com.selenium.sdjubbs.common.util.Result;
import com.selenium.sdjubbs.common.util.StringUtil;
import com.selenium.sdjubbs.common.util.VerifyCodeUtil;
import com.sun.media.jfxmedia.logging.Logger;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@io.swagger.annotations.Api(value = "SDJUBBS Admin API", tags = "SDJUBBS Admin API")
public class AdminApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private SdjubbsSetting setting;

    @GetMapping(Api.USER)
    @ApiOperation(value = "获取所有的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页记录数", required = true, example = "10"),
            @ApiImplicitParam(name = "order", value = "排序", required = false, example = "id asc"),
    })
    public Result getAllUser(String page, String limit, String order) {
        int pageSize = 0;
        int pageNum = 0;
        try {
            pageSize = Integer.valueOf(limit);
            pageNum = Integer.valueOf(page);
            order = StringUtil.humpToLine(order);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(Constant.REQUEST_PARAM_FORMAT_ERROR_CODE, Constant.REQUEST_PARAM_FORMAT_ERROR);
        }
        //获取第pageNum页,pageSize条内容
        PageHelper.startPage(pageNum, pageSize, order);
        List<User> users = userService.getAllUser();
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        if (users == null) {
            return Result.failure("暂时没有用户");
        }
        return Result.success().add("pageInfo", pageInfo);
    }

    @PutMapping(Api.USER + "/{id}")
    @ApiOperation(value = "修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, example = "1"),
            @ApiImplicitParam(name = "username", value = "用户名(长度:4-12)", required = false, example = "test"),
            @ApiImplicitParam(name = "password", value = "密码(md5加密)", required = false, example = "3a42503923d841ac9b7ec83eed03b450"),
            @ApiImplicitParam(name = "salt", value = "加密salt", required = false, example = "48aca90-2"),
            @ApiImplicitParam(name = "age", value = "年龄", required = false, example = "0"),
            @ApiImplicitParam(name = "gender", value = "性别(0:男,1:女,2:未知)", required = false, example = "2"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false, example = "895484122@qq.com"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = false, example = "00000000000"),
            @ApiImplicitParam(name = "headPicture", value = "头像", required = false, example = "/common/images/0.jpg"),
            @ApiImplicitParam(name = "registerTime", value = "注册时间", required = false, example = "2019-09-01 23:37:49"),
            @ApiImplicitParam(name = "lastLoginTime", value = "上次登录时间", required = false, example = "2019-09-01 23:37:49"),
            @ApiImplicitParam(name = "status", value = "用户状态(0:有效,1:禁用)", required = false, example = "0"),
    })
    public Result updateUser(@PathVariable Integer id, User user) {
        Integer count = 0;
        try {
            count = userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(Constant.REQUEST_PARAM_FORMAT_ERROR_CODE, Constant.REQUEST_PARAM_FORMAT_ERROR);
        }
        if (count == 0) {
            return Result.failure(Constant.LOGIN_USER_NOT_EXIST_CODE, Constant.LOGIN_USER_NOT_EXIST);
        }
        return Result.success();
    }

    @DeleteMapping(Api.USER + "/{id}")
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, example = "1"),
    })
    public Result deleteUser(@PathVariable Integer id) {
        Integer count = 0;
        try {
            count = userService.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(Constant.REQUEST_PARAM_FORMAT_ERROR_CODE, Constant.REQUEST_PARAM_FORMAT_ERROR);
        }
        if (count == 0) {
            return Result.failure(Constant.LOGIN_USER_NOT_EXIST_CODE, Constant.LOGIN_USER_NOT_EXIST);
        }
        return Result.success();
    }

    @GetMapping(Api.VERIFY_CODE)
    @ApiOperation(value = "获取验证码")
    public Result getVerifyCode(int width, int height) {
        String savePath = setting.getVerifyCodeSavePath();
        log.info("verify code save path: " + savePath);
        try {
            VerifyCodeUtil.drawVerifyCode(width, height,setting.getVerifyCodeSavePath(),"test");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success();
    }
}
