package com.selenium.sdjubbs.common.scheduler;

import com.selenium.sdjubbs.common.job.SpiderJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpiderJobScheduler {
    @Autowired
    private Scheduler scheduler;

    public void startSpiderJob() throws SchedulerException {
        //创建JobDetail实例，该实例与Job绑定
        JobDetail jobDetail = JobBuilder
                .newJob(SpiderJob.class)
                .withIdentity("spiderJob")
                .build();
        //创建CronTrigger触发器实例,定义job每天1:00执行一次
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("spiderTrigger")
                .startNow()
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0 0 1 * * ?")
                )
                .build();
        //创建scheduler实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
