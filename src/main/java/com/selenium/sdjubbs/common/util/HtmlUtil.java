package com.selenium.sdjubbs.common.util;

import org.springframework.stereotype.Component;


public class HtmlUtil {
    public static String htmlFilter(String s) {
        if(s==null||s.equals("")) {
            return s;
        }
        String str=s.replaceAll("<[.[^<]]*>","");
        return str;

    }

}
