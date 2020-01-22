package com.selenium.sdjubbs.common.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selenium.sdjubbs.common.api.Api;
import com.selenium.sdjubbs.common.bean.Article;
import com.selenium.sdjubbs.common.bean.Block;
import com.selenium.sdjubbs.common.bean.User;
import com.selenium.sdjubbs.common.config.SdjubbsSetting;
import com.selenium.sdjubbs.common.service.ArticleService;
import com.selenium.sdjubbs.common.service.BlockService;
import com.selenium.sdjubbs.common.service.RedisService;
import com.selenium.sdjubbs.common.service.UserService;
import com.selenium.sdjubbs.common.util.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@io.swagger.annotations.Api(value = "SDJUBBS Admin API", tags = "SDJUBBS Admin API")
public class AdminApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SdjubbsSetting setting;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BlockService blockService;

    @GetMapping(Api.USER)
    @ApiOperation(value = "获取所有的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "登录身份凭证", required = true, example = "test"),
            @ApiImplicitParam(name = "sessionId", value = "cookie中存的值", required = true, example = "A7D3515256A097709011A5EBB86D9FEF"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页记录数", required = true, example = "10"),
            @ApiImplicitParam(name = "order", value = "排序", required = false, example = "id asc"),
    })
    protected Result getAllUser(String name, String sessionId, String page, String limit, String order) {
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
            @ApiImplicitParam(name = "name", value = "登录身份凭证", required = true, example = "test"),
            @ApiImplicitParam(name = "sessionId", value = "cookie中存的值", required = true, example = "A7D3515256A097709011A5EBB86D9FEF"),
            @ApiImplicitParam(name = "id", value = "用户id", required = true, example = "1"),
            @ApiImplicitParam(name = "username", value = "用户名(长度:4-12)", required = false, example = "test"),
            @ApiImplicitParam(name = "password", value = "密码(md5加密)", required = false, example = "3a42503923d841ac9b7ec83eed03b450"),
            @ApiImplicitParam(name = "salt", value = "加密salt", required = false, example = "48aca90-2"),
            @ApiImplicitParam(name = "age", value = "年龄", required = false, example = "0"),
            @ApiImplicitParam(name = "gender", value = "性别(0:男,1:女,2:未知)", required = false, example = "2"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false, example = "895484122@qq.com"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = false, example = "00000000000"),
            @ApiImplicitParam(name = "headPicture", value = "头像", required = false, example = "/common/images/avatar/default.jpg"),
            @ApiImplicitParam(name = "registerTime", value = "注册时间", required = false, example = "2019-09-01 23:37:49"),
            @ApiImplicitParam(name = "lastLoginTime", value = "上次登录时间", required = false, example = "2019-09-01 23:37:49"),
            @ApiImplicitParam(name = "status", value = "用户状态(0:有效,1:禁用)", required = false, example = "0"),
    })
    protected Result updateUser(String name, String sessionId, @PathVariable Integer id, User user, @RequestParam(value = "file", required = false) MultipartFile file) {
        log.info("update user: " + user);
        if (file != null) {
            String savePath = PhotoUtil.saveFile(file, setting.getAvatarSavePath()).split("/static")[1];
            user.setHeadPicture(savePath);
        }
        if (user.getPassword() != null) {
            //通过MD5+随机salt加密写入数据库的密码
            String salt = UUID.randomUUID().toString().substring(1, 10);
            user.setSalt(salt);
            String password = MD5Util.dbEncryption(user.getPassword(), salt);
            user.setPassword(password);
        }
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

    @PostMapping(Api.USER)
    @ApiOperation(value = "新增用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "登录身份凭证", required = true, example = "test"),
            @ApiImplicitParam(name = "sessionId", value = "cookie中存的值", required = true, example = "A7D3515256A097709011A5EBB86D9FEF"),
            @ApiImplicitParam(name = "username", value = "用户名(长度:4-12)", required = false, example = "test"),
            @ApiImplicitParam(name = "password", value = "密码(md5加密)", required = false, example = "3a42503923d841ac9b7ec83eed03b450"),
            @ApiImplicitParam(name = "age", value = "年龄", required = false, example = "0"),
            @ApiImplicitParam(name = "gender", value = "性别(0:男,1:女,2:未知)", required = false, example = "2"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, example = "895484122@qq.com"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = false, example = "00000000000"),
            @ApiImplicitParam(name = "headPicture", value = "头像", required = false, example = "/common/images/avatar/default.jpg"),
    })
    protected Result addUser(String name, String sessionId, @Valid User user, BindingResult bindingResult, @RequestParam(value = "file", required = false) MultipartFile file) {
        log.info("add user: " + user);
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError error : allErrors) {
                return Result.failure(Constant.REGISTER_USER_FORMAT_ERROR_CODE, error.getDefaultMessage());
            }
        }
        if (file != null) {
            String savePath = PhotoUtil.saveFile(file, setting.getAvatarSavePath()).split("/static")[1];
            user.setHeadPicture(savePath);
        } else {
            user.setHeadPicture(Constant.DEFAULT_HEAD_PICTURE);
        }
        //通过MD5+随机salt加密写入数据库的密码
        String salt = UUID.randomUUID().toString().substring(1, 10);
        user.setSalt(salt);
        String password = MD5Util.dbEncryption(user.getPassword(), salt);
        user.setPassword(password);
        user.setRegisterTime(TimeUtil.getTime());
        user.setLastLoginTime(TimeUtil.getTime());
        user.setStatus(0);
        user.setRole(0);
        userService.addUser(user);
        return Result.success();
    }


    @DeleteMapping(Api.USER + "/{id}")
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "登录身份凭证", required = true, example = "test"),
            @ApiImplicitParam(name = "sessionId", value = "cookie中存的值", required = true, example = "A7D3515256A097709011A5EBB86D9FEF"),
            @ApiImplicitParam(name = "id", value = "用户id", required = true, example = "1"),
    })
    protected Result deleteUser(String name, String sessionId, @PathVariable Integer id) {
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

    @DeleteMapping(Api.USERS)
    @ApiOperation(value = "批量删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "登录身份凭证", required = true, example = "test"),
            @ApiImplicitParam(name = "sessionId", value = "cookie中存的值", required = true, example = "A7D3515256A097709011A5EBB86D9FEF"),
            @ApiImplicitParam(name = "ids", value = "用户id集合，逗号进行分割", required = true, example = "1"),
    })
    protected Result deleteUserByBatch(String name, String sessionId, @RequestParam("ids") String ids) {
        List<Integer> idList = new ArrayList<>();
        String[] idsTemp = ids.split(",");
        for (String ip : idsTemp) {
            idList.add(Integer.valueOf(ip));
        }
        userService.deleteUserByBatch(idList);
        return Result.success();
    }


    @GetMapping(Api.VERIFY_CODE)
    @ApiOperation(value = "获取验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "width", value = "验证码图片宽", required = true, example = "180"),
            @ApiImplicitParam(name = "height", value = "验证码图片高", required = true, example = "50"),
    })
    public Result getVerifyCode(int width, int height, HttpServletRequest request) {
        String ip = MD5Util.md5(request.getRemoteAddr()).substring(0, 10);
        String imagePath = "/common/" + setting.getVerifyCodeSavePath().split("/common/")[1];
        String savePath = System.getProperty("user.dir") + setting.getVerifyCodeSavePath();
        FileUtil.deleteFilesWithPrefix(savePath, ip);
        String imageName = ip + "_" + System.currentTimeMillis();
        String verifyCode = "";
        String recordId = "";
        String verifyCodeKey = "";
        try {
            verifyCode = VerifyCodeUtil.drawVerifyCode(width, height, savePath, imageName);
            recordId = System.currentTimeMillis() + UUID.randomUUID().toString();
            verifyCodeKey = "verifycode:" + ip + ":" + recordId;
            //60秒后验证码失效
            redisService.set(verifyCodeKey, verifyCode, 60);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success().add("img", imagePath + "/" + imageName).add("recordId", recordId);
    }


    @PostMapping(Api.LOGIN)
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名(长度:4-12)", required = true, example = "test"),
            @ApiImplicitParam(name = "password", value = "密码(md5加密)", required = true, example = "3a42503923d841ac9b7ec83eed03b450"),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, example = "3111"),
            @ApiImplicitParam(name = "recordId", value = "验证码唯一标识", required = true, example = "3a42503923d841ac9b7ec83eed03b450")
    })
    public Result login(String username, String password, String verifyCode, String recordId, HttpServletRequest request, HttpSession session) {
        String ip = MD5Util.md5(request.getRemoteAddr()).substring(0, 10);
        String verifyCodeKey = "verifycode:" + ip + ":" + recordId;
        String realVerifyCode = redisService.get(verifyCodeKey);
        if (verifyCode == null || realVerifyCode == null || (!verifyCode.equalsIgnoreCase(realVerifyCode))) {
            return Result.failure(Constant.VERIFY_CODE_WRONG_CODE, Constant.VERIFY_CODE_WRONG);
        }
        log.info("username: " + username + " password: " + password + " verifyCode: " + verifyCode + " recordId: " + recordId);
        Subject subject = SecurityUtils.getSubject();
        log.info("用户是否登录,isAuthenticated: " + subject.isAuthenticated());
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return Result.failure(Constant.LOGIN_USER_NOT_EXIST_CODE, Constant.LOGIN_USER_NOT_EXIST);
        } else if (user.getRole() == 0) {//普通用户
            return Result.failure(Constant.LOGIN_USER_NOT_ADMIN_CODE, Constant.LOGIN_USER_NOT_ADMIN);
        }
        // 判断当前用户是否登录
//        if (!subject.isAuthenticated()) {
        String salt = user.getSalt();
        String realPassword = MD5Util.dbEncryption(password, salt);
        // 将用户名和密码封装
        UsernamePasswordToken token = new UsernamePasswordToken(username, realPassword);
        try {
            // 登录
            subject.login(token);
            log.info("login success");
            //存入redis key:username value: md5(ip+sessionId)
            String sessionId = session.getId();
            redisService.set("admin:name:" + username, MD5Util.md5(request.getRemoteAddr() + sessionId));
            return Result.success().add("username", username).add("sessionId", sessionId);

        } catch (AuthenticationException e) {
            log.info("login failure");
            e.printStackTrace();
            return Result.failure(Constant.LOGIN_USER_WRONG_PASSWORD_CODE, Constant.LOGIN_USER_WRONG_PASSWORD);
        }
//        }
    }


    //--------------------------------文章管理-----------------------------
    @GetMapping(Api.ARTICLE)
    @ApiOperation(value = "获取所有的文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "登录身份凭证", required = true, example = "test"),
            @ApiImplicitParam(name = "sessionId", value = "cookie中存的值", required = true, example = "A7D3515256A097709011A5EBB86D9FEF"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页记录数", required = true, example = "10"),
            @ApiImplicitParam(name = "order", value = "排序", required = false, example = "id asc"),
    })
    protected Result getAllArticle(String name, String sessionId, String page, String limit, String order) {
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
        List<Article> articles = articleService.getAllArticle();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        if (articles == null) {
            return Result.failure("暂时没有文章");
        }
        return Result.success().add("pageInfo", pageInfo);
    }

    @PutMapping(Api.ARTICLE + "/{id}")
    @ApiOperation(value = "修改文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "登录身份凭证", required = true, example = "test"),
            @ApiImplicitParam(name = "sessionId", value = "cookie中存的值", required = true, example = "A7D3515256A097709011A5EBB86D9FEF"),
    })
    protected Result updateArticle(String name, String sessionId, @PathVariable Integer id, Article article) {
        log.info("update article: " + article);

        Integer count = 0;
        try {
            count = articleService.updateArticle(article);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(Constant.REQUEST_PARAM_FORMAT_ERROR_CODE, Constant.REQUEST_PARAM_FORMAT_ERROR);
        }
        if (count == 0) {
            return Result.failure(Constant.LOGIN_USER_NOT_EXIST_CODE, Constant.LOGIN_USER_NOT_EXIST);
        }
        return Result.success();
    }

    @DeleteMapping(Api.ARTICLE + "/{id}")
    @ApiOperation(value = "删除文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "登录身份凭证", required = true, example = "test"),
            @ApiImplicitParam(name = "sessionId", value = "cookie中存的值", required = true, example = "A7D3515256A097709011A5EBB86D9FEF"),
            @ApiImplicitParam(name = "id", value = "文章id", required = true, example = "1"),
    })
    protected Result deleteArticle(String name, String sessionId, @PathVariable Integer id) {
        Integer count = 0;
        try {
            count = articleService.deleteArticle(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(Constant.REQUEST_PARAM_FORMAT_ERROR_CODE, Constant.REQUEST_PARAM_FORMAT_ERROR);
        }
        if (count == 0) {
            return Result.failure(Constant.LOGIN_USER_NOT_EXIST_CODE, Constant.LOGIN_USER_NOT_EXIST);
        }
        return Result.success();
    }

    @DeleteMapping(Api.ARTICLES)
    @ApiOperation(value = "批量删除文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "登录身份凭证", required = true, example = "test"),
            @ApiImplicitParam(name = "sessionId", value = "cookie中存的值", required = true, example = "A7D3515256A097709011A5EBB86D9FEF"),
            @ApiImplicitParam(name = "ids", value = "文章id集合，逗号进行分割", required = true, example = "1"),
    })
    protected Result deleteArticleByBatch(String name, String sessionId, @RequestParam("ids") String ids) {
        List<Integer> idList = new ArrayList<>();
        String[] idsTemp = ids.split(",");
        for (String ip : idsTemp) {
            idList.add(Integer.valueOf(ip));
        }
        articleService.deleteArticleByBatch(idList);
        return Result.success();
    }

    //-------------------------------------板块相关------------------------------

    /**
     * method: get
     * url: /block
     * description: 获得所有板块
     */
    @GetMapping(Api.BLOCK)
    @ApiOperation(value = "获得所有板块")
    public Result getAllBlock() {
        List<Block> blocks = blockService.getAllBlock();
        return Result.success().add("blocks", blocks);
    }

    //---------------------------------------新增加文章------------------------------
    @PostMapping(Api.ARTICLE)
    @ApiOperation(value = "新增文章")
    protected Result addArticle(String name, String sessionId, Article article) {
        log.info("name: " + name + " article: " + article);
        Block block = blockService.getBlockById(article.getBlockId());
        article.setBlockName(block.getTitle());
        User user = userService.getUserByUsername(name);
        article.setAuthorId(user.getId());
        article.setAuthorName(user.getUsername());
        article.setCreateTime(TimeUtil.getTime());
        article.setPriority(0);
        articleService.addArticle(article);
        return Result.success();
    }

}
