package com.example.springbootscheduling.scheduled;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author Hoxton
 * @version 1.1.0
 */
public class HelloQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Hello, Quartz! – executing its JOB at "+
                new Date() + " by " + context.getTrigger().toString());
    }
}
