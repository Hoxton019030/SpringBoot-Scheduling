package com.example.springbootscheduling.scheduled;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Hoxton
 * @version 1.1.0
 */
public class QuartzTest2 {
    public static void main(String[] args) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("dummyJobName", "group1").build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyJobName", "group1").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job,trigger);

    }
}

class HelloJob implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello Quartz");
    }
}