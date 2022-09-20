package Email;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author Hoxton
 * @version 1.1.0
 */
public class Email {
    public static void main(String[] args) throws SchedulerException {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        JobDetail jobDetail = JobBuilder.newJob(SendEmail.class)
                .withIdentity("job1","group1")
                .build();

        //一秒觸發一次
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(1))
                .build();
        Date date = new Date();

        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(date)
                .build();

        CronTrigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("40 * * * * ?"))
                .build();


        TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1").build();

        scheduler.scheduleJob(jobDetail, trigger2);
        scheduler.start();


    }
}
