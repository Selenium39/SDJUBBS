package com.selenium.sdjubbs.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public  class TimeUtil {
    public static String getTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.format(now);
    }
}
