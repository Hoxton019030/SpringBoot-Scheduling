package com.example.springbootscheduling.scheduled;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Hoxton
 * @version 1.1.0
 */
public class QuartzTest {
    // 你需要在start和shutdown之間執行你的任務。
    public static void main(String[] args) {

        try {
            // 從工廠中獲取Scheduler示例
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // 開始
            scheduler.start();

            // 定義Job，並將其繫結到HelloJob類中
            JobDetail job = JobBuilder.newJob(HelloJob123.class).withIdentity("job1", "group1") // name 和 group
                    .usingJobData("username", "天喬巴夏") // 置入JobDataMap
                    .usingJobData("age", "20")
                    .withDescription("desc-demo")
                    .build();

            // 觸發Job執行，每40s執行一次
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow() // 立即開始
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();

            // 告訴 quartz 使用trigger來排程job
            scheduler.scheduleJob(job, trigger);
            // 關閉，執行緒終止
            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

}

@Slf4j
@NoArgsConstructor
class HelloJob123 implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 從context中獲取屬性
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        JobKey key = jobDetail.getKey();
        Class<? extends Job> jobClass = jobDetail.getJobClass();
        String description = jobDetail.getDescription();

        String username = jobDataMap.getString("username");
        int age = jobDataMap.getIntValue("age");

        log.info("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }
}


