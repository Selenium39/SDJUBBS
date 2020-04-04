package com.selenium.sdjubbs.common.util;

import com.selenium.sdjubbs.common.bean.SystemInfo;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class ServerInfoUtil {
    //系统信息
    public static SystemInfo getSystemInfo() {
        SystemInfo systemInfo = null;
        try {
            Runtime r = Runtime.getRuntime();
            Properties props = System.getProperties();
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            Map<String, String> map = System.getenv();
            String userName = map.get("USERNAME");// 获取用户名
            String computerName = map.get("COMPUTERNAME");// 获取计算机名
            String userDomain = map.get("USERDOMAIN");// 获取计算机域名
            String hostName = addr.getHostName();
            Long totalMemory = r.totalMemory();
            Long freeMemory = r.freeMemory();
            Integer availableProcessors = r.availableProcessors();
            String jdkVersion = props.getProperty("java.version");
            String jdkHome = props.getProperty("java.home");
            String osName = props.getProperty("os.name");
            String osArch = props.getProperty("os.arch");
            String osVersion = props.getProperty("os.version");
            String accountName = props.getProperty("user.name");
            String workDir = props.getProperty("user.dir");
//            log.info("用户名:    " + userName);
//            log.info("计算机名:    " + computerName);
//            log.info("计算机域名:    " + userDomain);
//            log.info("本地ip地址:    " + ip);
//            log.info("本地主机名:    " + hostName);
//            log.info("JVM可以使用的总内存:    " + totalMemory);
//            log.info("JVM可以使用的剩余内存:    " + freeMemory);
//            log.info("JVM可以使用的处理器个数:    " + availableProcessors);
//            log.info("Java的运行环境版本：    " + jdkVersion);
//            log.info("Java的安装路径：    " + jdkHome);
//            log.info("操作系统的名称：    " + osName);
//            log.info("操作系统的构架：    " + osArch);
//            log.info("操作系统的版本：    " + osVersion);
//            log.info("用户的账户名称：    " + accountName);
//            log.info("用户的当前工作目录：    " + workDir);
            systemInfo = new SystemInfo(userName, computerName, userDomain, ip, hostName, totalMemory, freeMemory, availableProcessors, jdkVersion, jdkHome, osName, osArch, osVersion, accountName, workDir);
        } catch (Exception e) {
            e.printStackTrace();
            return systemInfo;
        }
        return systemInfo;
    }
}
