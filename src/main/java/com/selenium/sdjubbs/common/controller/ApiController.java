package com.selenium.sdjubbs.common.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selenium.sdjubbs.common.api.Api;
import com.selenium.sdjubbs.common.bean.*;
import com.selenium.sdjubbs.common.service.*;
import com.selenium.sdjubbs.common.util.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * description:返回数据API
 * attention: 1.所有需要登录后才能操作的方法设为protected
 */
@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@io.swagger.annotations.Api(value = "SDJUBBS API",tags = "SDJUBBS API")
public class ApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BlockService blockService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;


    /**
     * method: post
     * url: /register
     * description: 注册
     */
    @PostMapping(Api.REGISTER)
    @ApiOperation(value = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = false, example = "1"),
            @ApiImplicitParam(name = "username", value = "用户名(长度:4-12)", required = true, example = "test"),
            @ApiImplicitParam(name = "password", value = "密码(md5加密)", required = true, example = "3a42503923d841ac9b7ec83eed03b450"),
            @ApiImplicitParam(name = "salt", value = "加密salt", required = false, example = "48aca90-2"),
            @ApiImplicitParam(name = "age", value = "年龄", required = false, example = "0"),
            @ApiImplicitParam(name = "gender", value = "性别(0:男,1:女,2:未知)", required = false, example = "2"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, example = "895484122@qq.com"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = false, example = "00000000000"),
            @ApiImplicitParam(name = "headPicture", value = "头像", required = false, example = "/common/images/0.jpg"),
            @ApiImplicitParam(name = "registerTime", value = "注册时间", required = false, example = "2019-09-01 23:37:49"),
            @ApiImplicitParam(name = "lastLoginTime", value = "上次登录时间", required = false, example = "2019-09-01 23:37:49"),
            @ApiImplicitParam(name = "status", value = "用户状态(0:有效,1:禁用)", required = false, example = "0"),
            @ApiImplicitParam(name = "role", value = "用户角色(0:普通用户,1:管理员)",required = false,example = "0")
    })
    public Result register(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError error : allErrors) {
                return Result.failure(Constant.REGISTER_USER_FORMAT_ERROR_CODE, error.getDefaultMessage());
            }
        }
        //通过MD5+随机salt加密写入数据库的密码
        String salt = UUID.randomUUID().toString().substring(1, 10);
        user.setSalt(salt);
        String password = MD5Util.dbEncryption(user.getPassword(), salt);
        user.setPassword(password);
        //用户初始化操作
        user.setAge(0);
        user.setGender(2);
        user.setPhone("00000000000");
        user.setHeadPicture("/common/images/0.jpg");
        user.setRegisterTime(TimeUtil.getTime());
        user.setLastLoginTime(TimeUtil.getTime());
        user.setStatus(0);
        user.setRole(0);
        try {
            userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success()
                .add("username", user.getUsername())
                .add("email", user.getEmail());
    }

    /**
     * method: post
     * url: /login
     * description: 登录
     */
    @PostMapping(Api.LOGIN)
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名(长度:4-12)", required = true, example = "test"),
            @ApiImplicitParam(name = "password", value = "密码(md5加密)", required = true, example = "3a42503923d841ac9b7ec83eed03b450"),
    })
    public Result login(String username, String password, HttpSession session, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        User user = userService.getUserByUsername(username);
        if (user != null) {
            if (user.getStatus() == 1) {
                return Result.failure(Constant.FAILURE_CODE, Constant.LOGIN_USER_FORBID_CODE, Constant.LOGIN_USER_FORBID);
            }
            String salt = user.getSalt();
            String realPassword = MD5Util.dbEncryption(password, salt);
            if (password != null && salt != null && realPassword.equals(user.getPassword())) {
                //存入redis key:username value: md5(ip+sessionId)
                //value加入ip是为了防止别人拿到sessionId
                String redisValue=MD5Util.md5(ip+session.getId());
                redisService.set("user:name:"+username, redisValue);
                return Result.success().add("username", username).add("sessionId", session.getId());
            } else {
                return Result.failure(Constant.FAILURE_CODE, Constant.LOGIN_USER_WRONG_PASSWORD_CODE, Constant.LOGIN_USER_WRONG_PASSWORD);
            }
        } else {
            return Result.failure(Constant.FAILURE_CODE, Constant.LOGIN_USER_NOT_EXIST_CODE, Constant.LOGIN_USER_NOT_EXIST);
        }
    }

    /**
     * method: post
     * url: /logout
     * description: 退出
     */
    @PostMapping(Api.LOGOUT)
    @ApiOperation(value = "退出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名(长度:4-12)", required = true, example = "test"),
            @ApiImplicitParam(name = "sessionId", value = "cookie中存的值", required = true, example = "A7D3515256A097709011A5EBB86D9FEF"),
    })
    protected Result logout(String username, String sessionId) {
        if (redisService.delete("user:name:"+username)) {
            return Result.success();
        } else {
            return Result.failure();
        }
    }

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

    /**
     * method: get
     * url: /block/id
     * description: 根据板块id获取该板块下的所有文章
     */
    @GetMapping(Api.BLOCK + "/{id}")
    @ApiOperation(value = "根据板块id获取该板块下的所有文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "板块id", required = true, example = "1"),
            @ApiImplicitParam(name = "id", value = "页数", required = true, example = "1"),
    })
    public Result getBlock(@PathVariable("id") int id, @RequestParam("pn") int pn) {
        Block block = blockService.getBlockById(id);
        PageHelper.startPage(pn, Constant.PAGE_SIZE);
        List<Article> articles = articleService.getAllArticleByBlockId(id);
        PageInfo<Article> pageInfo = new PageInfo<>(articles, Constant.NAVIGATE_PAGE_SIZE);
        return Result.success().add("block", block).add("pageInfo", pageInfo);
    }

    /**
     * method: get
     * url: /article/id
     * description: 根据文章id获取文章和文章下所有评论
     */
    @GetMapping(Api.ARTICLE + "/{id}")
    @ApiOperation(value = "根据文章id获取文章和文章下所有评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id", required = true, example = "1"),
    })
    public Result getArticle(@PathVariable("id") int id) {
        Article article = articleService.getArticleById(id);
        List<Comment> comments = commentService.getCommentByArticleId(id);
        return Result.success().add("article", article).add("comments", comments);
    }

    /**
     * method: get
     * url: /comment/id
     * description: 根据评论id获取评论和评论下所有回复
     */
    @GetMapping(Api.COMMENT + "/{id}")
    @ApiOperation(value = "根据评论id获取评论和评论下所有回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "评论id", required = true, example = "1"),
    })
    public Result getComment(@PathVariable("id") int id) {
        Comment comment = commentService.getCommentById(id);
        List<Reply> replys = replyService.getReplyByCommentId(id);
        return Result.success().add("comment", comment).add("replys", replys);
    }

    /**
     * method: post
     * url: /comment
     * description: 增加一条评论
     */
    @PostMapping(Api.COMMENT)
    @ApiOperation(value = "增加一条评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名(长度:4-12)", required = true, example = "test"),
            @ApiImplicitParam(name = "sessionId", value = "cookie中存的值", required = true, example = "A7D3515256A097709011A5EBB86D9FEF"),
            @ApiImplicitParam(name = "id", value = "评论id", required = false, example = "1"),
            @ApiImplicitParam(name = "content", value = "评论内容", required = true, example = "content"),
            @ApiImplicitParam(name = "createTime", value = "评论创建时间", required = false, example = "2019-09-01 23:37:49"),
            @ApiImplicitParam(name = "articleId", value = "文章id", required = true, example = "1"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, example = "1"),
            @ApiImplicitParam(name = "userName", value = "必须与username一致", required = true, example = "test"),
    })
    protected Result addComment(String username, String sessionId, Comment comment) {
        if (comment.getContent().trim() == null || comment.getContent().trim().length() == 0 || comment.getContent().trim().equals(" ")) {
            return Result.failure(Constant.USER_INPUT_EMPTY_CODE, Constant.USER_INPUT_EMPTY);
        }
        comment.setContent(HtmlUtil.htmlFilter(comment.getContent()));
        comment.setCreateTime(TimeUtil.getTime());
        comment.setUserId(userService.getUserByUsername(username).getId());
        commentService.addComment(comment);
        return Result.success().add("comment", comment);
    }

    /**
     * method: post
     * url: /comment
     * description: 增加一条回复
     */
    @PostMapping(Api.REPLY)
    @ApiOperation(value = "增加一条回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名(长度:4-12)", required = true, example = "test"),
            @ApiImplicitParam(name = "sessionId", value = "cookie中存的值", required = true, example = "A7D3515256A097709011A5EBB86D9FEF"),
            @ApiImplicitParam(name = "id", value = "回复id", required = false, example = "1"),
            @ApiImplicitParam(name = "content", value = "回复内容", required = true, example = "content"),
            @ApiImplicitParam(name = "createTime", value = "评论创建时间", required = false, example = "2019-09-01 23:37:49"),
            @ApiImplicitParam(name = "receiverUserId", value = "接收回复人的id", required = true, example = "1"),
            @ApiImplicitParam(name = "receiverUserName", value = "接收回复人的姓名", required = true, example = "test"),
            @ApiImplicitParam(name = "sendUserId", value = "发送回复人的id", required = true, example = "1"),
            @ApiImplicitParam(name = "sendUserName", value = "发送回复人的姓名", required = true, example = "test"),
    })
    protected Result addReply(String username, String sessionId, Reply reply) {
        if (reply.getContent() == null || reply.getContent().trim().length() == 0 || reply.getContent().trim().equals(" ")) {
            return Result.failure(Constant.USER_INPUT_EMPTY_CODE, Constant.USER_INPUT_EMPTY);
        }
        reply.setContent(HtmlUtil.htmlFilter(reply.getContent()));
        reply.setCreateTime(TimeUtil.getTime());
        reply.setSendUserId(userService.getUserByUsername(reply.getSendUserName()).getId());
        replyService.addReply(reply);
        return Result.success();
    }

}
