import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloJob {
    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail jobDetail = JobBuilder.newJob(SayHello.class)
                    .withIdentity("job1", "group1")
                    .usingJobData("username", "Hoxton")
                    .usingJobData("age", "20")
                    .withDescription("desc-demo")
                    .build();

            SimpleTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                    .build();

            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
//            scheduler.shutdown();

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}



