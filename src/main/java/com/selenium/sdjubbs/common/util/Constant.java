package com.selenium.sdjubbs.common.util;

public class Constant {
    public static final Integer PAGE_SIZE = 20;

    public static final Integer SUCCESS_CODE = 200;
    public static final String SUCCESS = "SUCCESS";

    public static final Integer FAILURE_CODE = 201;
    public static final String FAILURE = "FAILURE";

    public static final Integer LOGIN_USER_NOT_EXIST_CODE = 8001;
    public static final String LOGIN_USER_NOT_EXIST = "用户不存在";

    public static final Integer LOGIN_USER_WRONG_PASSWORD_CODE = 8002;
    public static final String LOGIN_USER_WRONG_PASSWORD = "密码错误";

    public static final Integer LOGIN_USER_FORBID_CODE = 8003;
    public static final String LOGIN_USER_FORBID = "用户被禁用";

    public static final Integer LOGIN_USER_IDLE_CODE = 8004;
    public static final String LOGIN_USER_IDLE = "用户尚未登录或已在其它处登录";

    public static final Integer REGISTER_USER_FORMAT_ERROR_CODE = 9000;
    public static final String REGISTER_USER_USERNAME_NULL = "昵称不能为空";
    public static final String REGISTER_USER_USERNAME_LENGTH = "昵称长度错误";
    public static final String REGISTER_USER_USERNAME_SUPPORT = "昵称只支持中文、英文、数字";
    public static final String REGISTER_USER_PASSWORD_NULL = "密码不能为空";
    public static final String REGISTER_USER_EMAIL_NULL = "邮箱不能为空";
    public static final String REGISTER_USER_EMAIL_FORMAT = "邮箱格式不正确";

    public static final Integer REGISTER_USER_USERNAME_EXIST_CODE = 9001;
    public static final String REGISTER_USER_USERNAME_EXIST = "用户名已被注册";

    public static final Integer REGISTER_USER_EMAIL_EXIST_CODE = 9002;
    public static final String REGISTER_USER_EMAIL_EXIST = "邮箱已被注册";


}
