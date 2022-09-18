# SpringBoot-Scheduling

用來做定期任務的東西，有兩種

+ Spring schedule：只適合處理簡單的計畫任務，不能處理分散式的任務，當任務太多時，可能出現阻塞、崩潰、延遲啟動等問題
+ Quartz：更強大的一個排程器，能夠配置上百甚至上千的事務。





# Spring Schedule

## 步驟

1. 在啟動類上加上@EnableScheduling

```java
@SpringBootApplication()
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

2. 在方法上加上@Scheduled

```java
@Component
public class Task {
    @Scheduled(fixedDelay = 10 * 100)
    public void printLocalTime() {
        System.out.println(LocalTime.now().toString());
    }
}
```

![image-20220914144530121](https://i.imgur.com/RJ1HtHT.png)



```java
    @Scheduled(cron = "1 * * * * ?",zone = "Asia/Taipei")
    public void printPer5second(){
        log.info("cron");
    }
```

![image-20220914145339544](https://i.imgur.com/AATFHO2.png)



```java
@Scheduled(cron = "0/1 * * * * ?",zone = "Asia/Taipei")
public void printPer5second(){
    log.info("cron");
}
```



![image-20220914145730980](https://i.imgur.com/Kby8DZf.png)





## Cron表達式

| 秒   | 分   | 時   | 日   | 月   | 周   |
| ---- | ---- | ---- | ---- | ---- | ---- |
| 1    | *    | *    | *    | *    | ?    |



想像一個時鐘...



| 當每次秒針指向一秒時        | 1 * * * * ?    |
| --------------------------- | -------------- |
| 當每次時針指向12時          | * * 12 * * ?   |
| 每個月 1 號中午 12 點       | 0 0 12 1 * ?   |
| 1 月每天中午 12 點，每 5 秒 | 0/5 0 12 * 1 ? |





| 字元 | 意義                                                         |
| :--- | :----------------------------------------------------------- |
| `*`  | 表達任意值，所有值都可以匹配                                 |
| `?`  | 只用在 `日` 跟 `周` 的值域，有點表達 don’t care 的概念，無所謂，不關心到底是什麼值 |
| `-`  | 指定範圍，前後接數字: 10-12                                  |
| `,`  | 指定離散的選項: 1,5,6,8，或者的概念                          |
| `/`  | 指定增量，表達 `每` 的概念: 0/5 意旨從 0 開始每 5 單位       |
| `L`  | 用在 `月` 跟 `周` 的值域。在月的話表達最後一天，在周的話前面可以加上數字 3L 表示該月最後一個星期二 |
| `W`  | 用在`日`的值域表示距離最近的該月工作日: 15W，距離 15 號最近的工作日，可能往前也可能往後 |
| `LW` | 用在`日`的值域，表示最後一周的工作日                         |
| `#`  | 用在`周`的值域，指定特定周的特定日: “4#2” 表示第二周的星期三 |
| `C`  | 用在`日`跟`周`的值域，指某特定個日期的後一天: 在`日`中寫 3C 指該月 3 號的後一天，在`周`中寫 2C 指該周星期一的後一天 |





# Quartz



![image-20220916143034403](https://i.imgur.com/C8FFRz9.png)

是一個開源的任務排程器，幾乎可以整合到任何Java應用程式中。

它可以用來

+ 安排每個晚上把資料庫內容轉存到檔案
+ 提供定時提醒服務
+ 族繁不及備載...



### 底層數據結構- 堆



#### 介紹

堆是一種特殊的樹，滿足下面兩個條件，就是一個**堆**

1. 堆是一顆完全二元樹`完整二元樹：除了最後一層以外，其他層的節點數需要達到最大(1,2,4,8. ..)`

![image-20220916103657827](https://i.imgur.com/GYIKgRe.png)

2. 每個節點所儲存的值，必須要大於`小頂堆`或小於`大頂堆`父節點的值

![image-20220916104946559](https://i.imgur.com/t5Aeict.png)

> 小頂堆



完整二元樹的一些特性：快速定位父節點

![image-20220916105634080](https://i.imgur.com/F9btok2.png)





**在排程器的框架中，每一個節點就是一個Job，越頻繁的Job會被放置在越上層的節點**



#### 如何插入元素

尾插法，在尾部插入一個新的元素，然後上浮，浮到符合定義

1. 除了最後一層以外，其他層的節點數需要達到最大(1,2,4,8. ..)，且最後一層節點都靠左排列
2. 每個節點所儲存的值，必須要大於`小頂堆`或小於`大頂堆`父節點的值

![image-20220916110137933](https://i.imgur.com/kGnnrnf.png)

![image-20220916110150486](https://i.imgur.com/YjWGVYi.png)

![image-20220916110656733](https://i.imgur.com/pqhvAgT.png)



#### 如何刪除元素

將尾部(最後一個節點)放置到堆頂，然後下沉

![image-20220916111249806](https://i.imgur.com/vM6IFnf.png)

![image-20220916111306206](https://i.imgur.com/ltoSIL1.png)

![image-20220916111330270](https://i.imgur.com/spOXHoC.png)

![image-20220916111958376](https://i.imgur.com/giEhi6Y.png)



### 時間輪算法(Time Wheel)

![image-20220916114613849](https://i.imgur.com/FGlGv1M.png)

再刪除元素的時候都需要下沉，當節點數很多時會造成性能延宕

![image-20220916113838923](https://i.imgur.com/djp3Uq4.png)



#### round 型的時間輪

任務上記錄一個round，遍歷到了就將round減一，為0時取出來執行

#### 分層時間輪

Cron表達式以及其他排程器所採用的底層架構，分成秒輪、分輪、小時輪、日輪、周輪、月輪、年輪...，月輪迭代到了，就將任務取出放到天輪中執行...，以此類推

##### Cron表達式

| 秒   | 分   | 時   | 日   | 月   | 周   |
| ---- | ---- | ---- | ---- | ---- | ---- |
| 1    | *    | *    | *    | *    | ?    |



想像一個時鐘...



| 當每次秒針指向一秒時        | 1 * * * * ?    |
| --------------------------- | -------------- |
| 當每次時針指向12時          | * * 12 * * ?   |
| 每個月 1 號中午 12 點       | 0 0 12 1 * ?   |
| 1 月每天中午 12 點，每 5 秒 | 0/5 0 12 * 1 ? |

##### JDK定時器:Timer







​	

## 重要API及概念



```java
public class Quartz {
    public static void main(String[] args) {
        MyJob myJob = new MyJob();
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "trigger1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        ;


    }
}
```

```java
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("MyJob execute:" + new Date());
    }
}
```





###  Scheduler

生命週期由ScheduleFactory建立開始，呼叫shutdown方法結束。

當Schduler建立，任何關於Schduling相關的事情，都由它控制

+ 新增
+ 刪除
+ 列出所有Job
+ 暫停觸發器

**在Start之前不會做任何事情**

### Job

你希望被排程器排程的任務元件介面，定義如何執行

+ 當Job的觸發器觸發時，排程程式的工作執行將呼叫excute()方法
+ 該方法接收一個`JobExcutionContext`物件，為Job提供了豐富的執行時環境，比如`schduler`,`trigger`,`jobDataMap`,`job`,`calender`,`time`

### JobDetail

用於定義Job的各種屬性、各種任務，還可以用來為Job儲存狀態資訊的JobDataMap

### Trigger

觸發任務執行，觸發器可能具有與Job有關的JobDataMap，以便將觸發器觸發的引數傳遞給Job，Quartz本身提供了幾種觸發器`SimpleTrigger`和`CronTrigger`是最常用到的。

SimpleTriger: 用於一次性執行作業或需要在給定的時間觸發一個作業並重複執行N次，且兩次執行時間有Delay

CronTrigger: 希望以日期作為觸發任務的板機，就用CronTriger

### JobBulider

用來建立[JobDetail](#JobDetail)

TriggerBulider

用於建立[Trigger](#Trigger)



