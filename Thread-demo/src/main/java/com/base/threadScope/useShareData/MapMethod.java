package com.base.threadScope.useShareData;

import java.util.HashMap;  
import java.util.Map;  
import java.util.Random;  
/*
 * 解决的问题：多个线程共享一个变量，怎样做的在各种的线程内，这个变量受其它线程的影响？
 * 
 * 
*/
public class MapMethod {  
  
      
    private static Map<Thread, Integer> threadData = new HashMap<Thread, Integer>();  
      
    public static void main(String[] args) {  
        //共启动2个线程  
        for(int i=0;i<2;i++){  
            //启动一个线程  
            new Thread(new Runnable(){  
                @Override  
                public void run() {  
                    int data = new Random().nextInt();  
                    System.out.println(Thread.currentThread().getName()   
                            + " has put data :" + data);  
                    //以当前线程为key值放入到map中，当取值时根据各自的线程取各自的数据  
                    threadData.put(Thread.currentThread(), data);  
                    new A().get();  
                    new B().get();  
                }  
            }).start();  
        }  
    }  
      
    static class A{  
        public void get(){  
            int data = threadData.get(Thread.currentThread());  
            System.out.println("A from " + Thread.currentThread().getName()   
                    + " get data :" + data);  
        }  
    }  
      
    static class B{  
        public void get(){  
            int data = threadData.get(Thread.currentThread());            
            System.out.println("B from " + Thread.currentThread().getName()   
                    + " get data :" + data);  
        }         
    }  
}  