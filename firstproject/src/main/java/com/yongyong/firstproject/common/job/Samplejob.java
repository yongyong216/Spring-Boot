package com.yongyong.firstproject.common.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Samplejob implements Job {

    // 실제 작업이 실행될 메서드
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // System.out.println("Scheduler!!");

    }

    // Job Detail 설정(Job의 메타데이터 설정)
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob()
                .ofType(Samplejob.class)
                .storeDurably()
                .withIdentity("Sample Job")
                .withDescription("Sample Job 테스트입니다.")
                .build();
    }

    // Trigger 설정 (반복할 스케줄 지정)
    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        CronScheduleBuilder schedule = CronScheduleBuilder.cronSchedule("* * * * * ?"); // 매 초마다 실행 된다.

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("Sample Trigger")
                .withDescription("Sample Trigger 입니다.")
                .withSchedule(schedule)
                .build();
    }

}
