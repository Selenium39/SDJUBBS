package com.selenium.sdjubbs.common.shiro;


import java.util.HashSet;
import java.util.Set;

import com.selenium.sdjubbs.common.bean.User;
import com.selenium.sdjubbs.common.service.UserService;
import com.selenium.sdjubbs.common.util.MD5Util;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author wantao
 * @date 2019-02-09 20:09
 * @description: 所有与数据库有关的安全数据应该封装到Realm中, 需要查询数据库并得到正确的数据
 */
@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    UserService UserService;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache
     * .shiro.authc.AuthenticationToken)
     * 1.获取认证消息.如果数据库中没有数据,返回null,如果得到正确的用户名和密码,返回指定类型的对象
     * 2.AuthenticationInfo可以使用SimpleAuthenticationInfo实现类,返回给你正确的用户名和密码
     * 3.token参数,就是我们需要的token
     */

    /**
     * @param token 前端的用户名和密码
     * @return AuthenticationInfo
     * @description 授权
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SimpleAuthenticationInfo info = null;
        // 1.将AuthenticationToken token转换为UserNamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        // 2.获取用户名
        String username = usernamePasswordToken.getUsername();
        // 3.查询数据库看是否存在指定的用户名和密码
        try {
            //这里以后从数据库中查
            User user = UserService.getUserByUsername(username);
            String salt = user.getSalt();
            String realPassword = MD5Util.dbEncryption(user.getPassword(), salt);
            Object principal = user.getUsername();// 用户
            Object credentials = realPassword;// 密码
            String realmName = this.getName();
            info = new SimpleAuthenticationInfo(principal, credentials, realmName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 4.如果查询到了,封装查询结果,返回给我们调用
        // 5.没有查询到,返回异常
        return info;
    }

    /**
     * @param principals  登录的用户名
     * @return AuthorizationInfo
     * @description 认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = null;
        String username = principals.toString();
        Set<String> roles = new HashSet<String>();
        roles.add("admin");
        info = new SimpleAuthorizationInfo(roles);
        return info;
    }
}