package com.example.springbootscheduling;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Hoxton
 * @version 1.1.0
 */
public class TimerTest {
    public static void main(String[] args) {
        Timer timer = new Timer(); //任務此時已經啟動
        for (int i = 0; i < 2; i++) {
            FooTimerTask task = new FooTimerTask("foo"+i);
            timer.schedule(task,new Date(),2000);// 任務的添加

        }
    }
}

class FooTimerTask extends TimerTask {
    private String name;

    public FooTimerTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        try {
            System.out.println("name" + name+",startTime="+ new Date());
            Thread.sleep(3000);
            System.out.println("name" + name+",EndTime="+ new Date());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
