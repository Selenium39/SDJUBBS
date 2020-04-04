package com.selenium.sdjubbs.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统相关信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfo {
    // 用户名
    private String username;
    // 计算机名
    private String computerName;
    //计算机域名
    private String userDomain;
    //ip
    private String ip;
    //本地主机名
    private String hostName;
    //jvm 总内存
    private Long totalMemory;
    //jvm 剩余内存
    private Long freeMemory;
    //处理器个数
    private Integer availableProcessors;
    //jdk 版本
    private String jdkVersion;
    //jdk 安装路径
    private String jdkHome;
    //操作系统名称
    private String osName;
    //操作系统架构
    private String osArch;
    //操作系统版本
    private String osVersion;
    //账户名称
    private String accountName;
    //工作目录
    private String workDir;

}
