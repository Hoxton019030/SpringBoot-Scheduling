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

        JobDetail jobDetail = JobBuilder.newJob(SendEmail.class).build();

        //一秒觸發一次
        Trigger trigger = TriggerBuilder.newTrigger()
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(1))
                        .build();

        Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.SECOND))
                .endAt()
                .build();


        scheduler.scheduleJob(jobDetail, trigger1);
        scheduler.start();



    }
}
