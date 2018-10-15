package com.concurrent.threadPool.newScheduledThreadPool;

import java.util.concurrent.Executors;  
import java.util.concurrent.ScheduledExecutorService;  
import java.util.concurrent.TimeUnit;  
  
public class ScheduledExecutorTest implements Runnable {  
    private String jobName = "";  
  
    public ScheduledExecutorTest(String jobName) {  
        super();  
        this.jobName = jobName;  
    }  
  
    @Override  
    public void run() {  
        System.out.println("execute " + jobName);  
    }  
  
    public static void main(String[] args) {  
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);  
  
        long initialDelay1 = 1;  
        long period1 = 1;  
        // 从现在开始1秒钟之后，每隔1秒钟执行一次job1 scheduleAtFixedRate  
        //每次执行时间为上一次任务开始起向后推一个时间间隔  
        //已固定的频率来执行某项计划(任务).固定的频率来执行某项计划，它不受计划执行时间的影响。到时间，它就执行。  
        service.scheduleAtFixedRate(  
                new ScheduledExecutorTest("job1"), initialDelay1,  
                period1, TimeUnit.SECONDS);  
  
        long initialDelay2 = 1;  
        long delay2 = 1;  
        // 从现在开始2秒钟之后，每隔2秒钟执行一次job2 scheduleWithFixedDelay  
        //每次执行时间为上一次任务结束起向后推一个时间间隔  
        //相对固定的延迟后，执行某项计划.即无论某个任务执行多长时间，等执行完了，我再延迟指定的时间。它受计划执行时间的影响。  
        service.scheduleWithFixedDelay(  
                new ScheduledExecutorTest("job2"), initialDelay2,  
                delay2, TimeUnit.SECONDS);  
    }  
}