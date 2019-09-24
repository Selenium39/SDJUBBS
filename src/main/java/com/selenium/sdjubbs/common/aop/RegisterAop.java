package com.selenium.sdjubbs.common.aop;

import com.selenium.sdjubbs.common.bean.User;
import com.selenium.sdjubbs.common.service.UserService;
import com.selenium.sdjubbs.common.util.Constant;
import com.selenium.sdjubbs.common.util.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegisterAop {
    @Autowired
    private UserService userService;

    @Around(value = "doRegister()")
    public Object beforeRegieter(ProceedingJoinPoint pjp) throws Throwable {
        User user = (User) pjp.getArgs()[0];
        if (userService.getUserByUsername(user.getUsername()) != null) {
            return Result.failure(Constant.REGISTER_USER_USERNAME_EXIST_CODE, Constant.REGISTER_USER_USERNAME_EXIST);
        }
        if (userService.getUserByEmail(user.getEmail()) != null) {
            return Result.failure(Constant.REGISTER_USER_EMAIL_EXIST_CODE, Constant.REGISTER_USER_EMAIL_EXIST);
        }
        return pjp.proceed();
    }

    @Pointcut("execution(public * com.selenium.sdjubbs.common.controller.ApiController.register(..))")
    public void doRegister() {
    }
}
