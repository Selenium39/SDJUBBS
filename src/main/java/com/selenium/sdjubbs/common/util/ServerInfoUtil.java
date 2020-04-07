package com.selenium.sdjubbs.common.util;

import com.selenium.sdjubbs.common.bean.CPUInfo;
import com.selenium.sdjubbs.common.bean.MemoryInfo;
import com.selenium.sdjubbs.common.bean.SystemInfo;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
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

    //内存信息
    public static MemoryInfo getMemoryInfo() {
        MemoryInfo memoryInfo = null;
        Sigar sigar = new Sigar();
        Long total;
        Long used;
        Long free;
        Mem mem = null;
        try {
            mem = sigar.getMem();
            total = mem.getTotal() / 1024L;
            used = mem.getUsed() / 1024L;
            free = mem.getFree() / 1024L;
            // 内存总量
            //log.info("内存总量:    " + total + "K av");
            // 当前内存使用量
            // log.info("当前内存使用量:    " + used + "K used");
            // 当前内存剩余量
            //log.info("当前内存剩余量:    " + free + "K free");
            memoryInfo = new MemoryInfo(total, used, free);
        } catch (SigarException e) {
            e.printStackTrace();
            return null;
        }
        return memoryInfo;
    }

    public static List<CPUInfo> getCPUInfos() {
        List<CPUInfo> cpuInfos = null;
        Sigar sigar = new Sigar();
        CpuInfo infos[] = null;
        CpuPerc cpuList[] = null;
        try {
            infos = sigar.getCpuInfoList();
            cpuList = sigar.getCpuPercList();
            cpuInfos = new ArrayList<>();
            for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
                //log.info("第" + (i + 1) + "块CPU信息");
                CpuPerc cpu = cpuList[i];
                int id = i + 1;
                String userUsed = CpuPerc.format(cpu.getUser());
                String systemUsed = CpuPerc.format(cpu.getSys());
                String wait = CpuPerc.format(cpu.getWait());
                String error = CpuPerc.format(cpu.getNice());
                String free = CpuPerc.format(cpu.getIdle());
                CPUInfo cpuInfo = new CPUInfo(id, userUsed, systemUsed, wait, error, free);
                cpuInfos.add(cpuInfo);
            }
        } catch (SigarException e) {
            e.printStackTrace();
            return null;
        }
        return cpuInfos;
    }

}
