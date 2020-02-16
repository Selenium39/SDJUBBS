package com.selenium.sdjubbs.common.job;

import com.selenium.sdjubbs.common.service.RedisService;
import com.selenium.sdjubbs.common.util.SpiderUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class SpiderJob implements Job {
    @Autowired
    private RedisService redisService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //job执行的时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("job execute time: " + simpleDateFormat.format(date));
        //具体的业务逻辑
        //开启爬虫爬取https://www.sdju.edu.cn首页图片地址并存到redis数据库
        String jsonString = SpiderUtil.getIndexNews();
        redisService.set("spider:name:news", jsonString);
    }
}
