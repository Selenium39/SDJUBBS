package com.selenium.sdjubbs.common.listener;

import com.selenium.sdjubbs.common.scheduler.SpiderJobScheduler;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
@Configuration
public class QuartzListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private SpiderJobScheduler scheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("===========启动============");
        try {
            //每天1点启动爬虫更新
            scheduler.startSpiderJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
