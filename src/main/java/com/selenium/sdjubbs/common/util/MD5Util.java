package com.selenium.sdjubbs.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5Util {
    public static String md5(String password) {
        return DigestUtils.md5Hex(password);
    }

    public static String dbEncryption(String password, String salt) {
        String str = salt.charAt(0) + salt.charAt(2) + password + salt.charAt(5);
        return md5(str);
    }

}

