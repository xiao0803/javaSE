package com.base.threadScope.useShareData;

import java.util.Random;  

/*
 * 解决的问题：多个线程共享一个变量，怎样做的在各种的线程内，这个变量受其它线程的影响？
 * 
 * ThreadLocal方式（用的是Map方式做的底层实现）
 * 
*/
public class ThreadLocalMethod {  
  
    private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();  
    public static void main(String[] args) {  
        for(int i=0;i<2;i++){  
            new Thread(new Runnable(){  
                @Override  
                public void run() {  
                    int data = new Random().nextInt();  
                    System.out.println(Thread.currentThread().getName()   
                            + " has put data :" + data);  
                    x.set(data);  
                    new A().get();  
                    new B().get();  
                }                             
            }).start();  
        }  
    }  
      
    static class A{  
        public void get(){  
            int data = x.get();  
            System.out.println("A from " + Thread.currentThread().getName()   
                    + " get data :" + data);  
        }  
    }  
      
    static class B{  
        public void get(){  
            int data = x.get();           
            System.out.println("B from " + Thread.currentThread().getName()   
                    + " get data :" + data);                      
        }         
    }  
}