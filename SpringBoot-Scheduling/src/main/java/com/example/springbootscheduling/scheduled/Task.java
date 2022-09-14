package com.example.springbootscheduling.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * @author Hoxton
 * @version 1.1.0
 */
@Component
@Slf4j
public class Task {
    @Scheduled(fixedDelay = 10 * 100)
    public void printLocalTime() {
        String time = LocalTime.now().toString();
        log.info(time);
    }

    @Scheduled(cron = "0/1 * * * * ?",zone = "Asia/Taipei")
    public void printPer5second(){
        log.info("cron");
    }
}
