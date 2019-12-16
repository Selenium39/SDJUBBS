package com.selenium.sdjubbs.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selenium.sdjubbs.common.api.Api;
import com.selenium.sdjubbs.common.bean.User;
import com.selenium.sdjubbs.common.service.UserService;
import com.selenium.sdjubbs.common.util.Constant;
import com.selenium.sdjubbs.common.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * limit:每次查询的页数
     * page:查询的页数
     */
    public Result getAllUser(String page, String limit) {
        log.info("limit: " + limit + " page: " + page);
        int pageSize = 0;
        int pageNum = 0;
        try {
            pageSize = Integer.valueOf(limit);
            pageNum = Integer.valueOf(page);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(Constant.REQUEST_PARAM_FORMAT_ERROR_CODE, Constant.REQUEST_PARAM_FORMAT_ERROR);
        }
        //获取第pageNum页,pageSize条内容
        //todo 排序
//        PageHelper.startPage(pageNum, pageSize,"id desc");
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userService.getAllUser();
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        if (users == null) {
            return Result.failure("暂时没有用户");
        }
        return Result.success().add("pageInfo", pageInfo);
    }
}
