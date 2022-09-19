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

        Trigger trigger = TriggerBuilder.newTrigger()
                        .
        build();

        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();



    }
}
