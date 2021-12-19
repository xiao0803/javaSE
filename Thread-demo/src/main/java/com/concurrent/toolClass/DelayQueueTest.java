package com.concurrent.toolClass;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<MyDelayed> queue = new DelayQueue<>();
        long currentTime = System.currentTimeMillis();
        queue.add(new MyDelayed("key1", 10, currentTime));
        queue.add(new MyDelayed("key2", 20, currentTime));
        queue.add(new MyDelayed("key3", 15, currentTime));
        queue.add(new MyDelayed("key4", 25, currentTime));
        queue.add(new MyDelayed("key5", 35, currentTime));

        System.out.println("会一直阻塞，直到元素过期");
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        //System.out.println(queue);
    }

}

class MyDelayed implements Delayed {
    private String key;
    private long currentTime;
    private long expireTime;

    public MyDelayed(String key, long expireTime, long currentTime) {
        this.key = key;
        this.expireTime = expireTime;
        this.currentTime = currentTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取剩余的时间
     *
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return expireTime - unit.MILLISECONDS.toSeconds(System.currentTimeMillis() - currentTime);
    }

    /**
     * 剩余时间升序排列
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        MyDelayed p = (MyDelayed) o;
        if (this.getDelay(TimeUnit.MICROSECONDS) > p.getDelay(TimeUnit.MICROSECONDS)){
            return 1;
        }
        if (this.getDelay(TimeUnit.MICROSECONDS) > p.getDelay(TimeUnit.MICROSECONDS)){
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "MyDelayed{" +
                "key='" + key + '\'' +
                ", currentTime=" + currentTime +
                ", expireTime=" + expireTime +
                '}';
    }
}