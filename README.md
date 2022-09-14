# SpringBoot-Scheduling

用來做定期任務的東西



# 步驟

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





# Cron表達式

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
| :--: | :----------------------------------------------------------- |
| `*`  | 表達任意值                                                   |
| `?`  | 只用在 `日` 跟 `周` 的值域，有點表達 don’t care 的概念       |
| `-`  | 指定範圍，前後接數字: 10-12                                  |
| `,`  | 指定離散的選項: 1,5,6,8                                      |
| `/`  | 指定增量，表達 `每` 的概念: 0/5 意旨從 0 開始每 5 單位       |
| `L`  | 用在 `月` 跟 `周` 的值域。在月的話表達最後一天，在周的話前面可以加上數字 3L 表示該月最後一個星期二 |
| `W`  | 用在`日`的值域表示距離最近的該月工作日: 15W，距離 15 號最近的工作日，可能往前也可能往後 |
| `LW` | 用在`日`的值域，表示最後一周的工作日                         |
| `#`  | 用在`周`的值域，指定特定周的特定日: “4#2” 表示第二周的星期三 |
| `C`  | 用在`日`跟`周`的值域，指某特定個日期的後一天: 在`日`中寫 3C 指該月 3 號的後一天，在`周`中寫 2C 指該周星期一的後一天 |