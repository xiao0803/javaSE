package com.base.lockAndCondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
实现效果：子线程循环10次，接着主线程循环100次，又回到子线程循环10次，接着再回到主线程又循环100次，如此循环50次

*/
public class ConditionCommunication {  
	  
    public static void main(String[] args) {  
          
        final Business business = new Business();  
        //创建了一个线程，并启动  
        new Thread(  
                new Runnable() {  
                      
                    @Override  
                    public void run() {                   
                        for(int i=1;i<=50;i++){  
                            business.sub(i);  
                        }                         
                    }  
                }  
        ).start();  
        //因为mian方法本身就占用一个线程，所以主线程不需要再new Thread  
        for(int i=1;i<=50;i++){  
            business.main(i);  
        }  
          
    }  
  
    static class Business {  
          
          Lock lock = new ReentrantLock();  
          Condition condition = lock.newCondition();  
          //决定是main执行还是sub执行  
          private boolean bShouldSub = true;  
          public  void sub(int i){  
              lock.lock();// 锁住了别的线程就不能进来了,包括下面的main()因为他们用的是同一把锁  
              try{  
                  //bShouldSub==false时等待  
                  while(!bShouldSub){  
                      try {  
                        condition.await();  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                  }  
                    for(int j=1;j<=10;j++){  
                        System.out.println("sub thread sequence of " + j + ",loop of " + i);  
                    }  
                  bShouldSub = false;  
                  condition.signal();  
              }finally{  
                  lock.unlock();  
              }  
          }  
            
          public  void main(int i){  
              lock.lock();  
              try{  
                 //bShouldSub==true时等待  
                 while(bShouldSub){  
                        try {  
                            condition.await();  
                        } catch (Exception e) {  
                            e.printStackTrace();  
                        }  
                    }  
                    for(int j=1;j<=100;j++){  
                        System.out.println("main thread sequence of " + j + ",loop of " + i);  
                    }  
                    bShouldSub = true;  
                    condition.signal();  
          }finally{  
            //如果中途抛出异常,那么这把锁就没有被解锁,别人就进不来了  
            //所以写在finally里面  
              lock.unlock();  
          }  
      }  
      
    }  
} 
