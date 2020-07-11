package com.concurrent.threadPool.newScheduledThreadPool;

import java.util.Timer;  
import java.util.TimerTask;  
  
public class TimerTest extends TimerTask {   
  
     private String jobName = "";   
  
     public TimerTest(String jobName) {   
         super();   
         this.jobName = jobName;   
     }   
  
     @Override   
     public void run() {   
         System.out.println("execute " + jobName);   
     }   
  
     public static void main(String[] args) {   
         Timer timer = new Timer();   
         long delay1 = 1 * 1000;   
         long period1 = 1000;   
         // 从现在开始1秒钟之后，每隔1秒钟执行一次 job1   
         timer.schedule(new TimerTest("job1"), delay1, period1);   
         long delay2 = 2 * 1000;   
         long period2 = 2000;   
         // 从现在开始2秒钟之后，每隔2秒钟执行一次 job2   
         timer.schedule(new TimerTest("job2"), delay2, period2);   
        }   
     }