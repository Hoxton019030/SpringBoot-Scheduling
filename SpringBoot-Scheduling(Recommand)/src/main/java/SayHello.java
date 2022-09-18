import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

@Slf4j
@NoArgsConstructor
public class SayHello implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();//從context中獲取屬性
        JobKey key = jobDetail.getKey();
        Class<? extends Job> jobClass = jobDetail.getJobClass();
        String description = jobDetail.getDescription();

        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String username = jobDataMap.getString("username");
        int age = jobDataMap.getIntValue("age");

        log.info("\nJobKey : {},\n JobClass : {},\n JobDesc : {},\n username : {},\n age : {}",
                key, jobClass.getName(), description, username, age);
    }
}
