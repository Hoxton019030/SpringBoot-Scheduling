package Email;

import org.quartz.*;

/**
 * @author Hoxton
 * @version 1.1.0
 */
public class SendEmail implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("SendEmail");
        JobKey key = context.getJobDetail().getKey();

    }
}
