package com.selenium.sdjubbs.common.aop;

import com.selenium.sdjubbs.common.service.RedisService;
import com.selenium.sdjubbs.common.util.Constant;
import com.selenium.sdjubbs.common.util.MD5Util;
import com.selenium.sdjubbs.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class AdminLoginStatusAop {
    @Autowired
    private RedisService redisService;

    @Around(value = "doApi()")
    public Object doApiBefore(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteAddr();
        log.info("请求ip: " + ip);
        try {
            Object[] args = pjp.getArgs();
            String username = args[0].toString();
            String sessionId = args[1].toString();
            log.info("username:" + username);
            log.info("sessionId:" + sessionId);
            String realSessionId = redisService.get("admin:name:" + username);
            //value加入ip是为了防止别人拿到sessionId
            sessionId = MD5Util.md5(ip + sessionId);
            if (realSessionId != null && sessionId.equals(realSessionId)) {
                return pjp.proceed();
            }else{
                return Result.failure(Constant.FAILURE_CODE, Constant.LOGIN_USER_IDLE_CODE, Constant.LOGIN_USER_IDLE);
            }
        } catch (Exception e) {
            log.info("doApiBefore抛出异常");
            e.printStackTrace();
            return Result.failure(Constant.FAILURE_CODE, Constant.LOGIN_USER_IDLE_CODE, Constant.LOGIN_USER_IDLE);
        }
    }

    @Pointcut("execution(protected * com.selenium.sdjubbs.common.controller.AdminApiController.*(..))")
    public void doApi() {
    }
}
