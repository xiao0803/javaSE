package com.concurrent.toolClass;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
模拟了100米赛跑，10名选手已经准备就绪（10个线程都走到begin.await()处；实际上有些没有到begin.await()处，begin对象的计数值就等于0了），
只等裁判一声令下（begin对象的计数值等于0）；所有人都到达终点时（end对象的计数值等于0），比赛结束。
*/
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        // 开始的倒数锁 
        final CountDownLatch begin = new CountDownLatch(1);  

        // 结束的倒数锁 
        final CountDownLatch end = new CountDownLatch(10);  

        // 十名选手 
        final ExecutorService exec = Executors.newFixedThreadPool(10);  

        for (int index = 0; index < 10; index++) {
            final int NO = index + 1;  
            Runnable run = new Runnable() {
                public void run() {  
                    try {  
                        // begin对象的计数值大于0就等待
                        begin.await();  
                        Thread.sleep((long) (Math.random() * 10000));  
                        System.out.println("No." + NO + " arrived");  
                    } catch (InterruptedException e) {  
                    } finally {  
                        // 每个选手到达终点时，end就减一
                        end.countDown();
                    }  
                }  
            };  
            exec.submit(run);
        }  
        System.out.println("Game Start");  
        // begin减一，开始游戏
        begin.countDown();  
        // 等待end变为0，即所有选手到达终点
        end.await();  
        System.out.println("Game Over");  
        exec.shutdown();  
    }
}
