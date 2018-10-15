package com.base.threadScope.useShareData;

import java.util.Random;

/*
 * 解决的问题：多个线程共享一个变量，怎样做的在各种的线程内，这个变量受其它线程的影响？
 * 
 * 
 * ThreadLocalMethod存在的问题：一个ThreadLocal代表一个变量，故其中只能放一个数据，如果你有两个变量要线程范围内共享，
则要定义两个ThreadLocal。如下为解决方案：
 * 
 * 
*/
public class ThreadLocalMethod2 {  
  
//  方式一  
//  private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();  
      
    private static ThreadLocal<MyThreadScopeData> myThreadScopeData = new ThreadLocal<MyThreadScopeData>();  
    public static void main(String[] args) {  
        for(int i=0;i<2;i++){  
            new Thread(new Runnable(){  
                @Override  
                public void run() {  
                    int data = new Random().nextInt();  
                    System.out.println(Thread.currentThread().getName()   
                            + " has put data :" + data);  
//                  方式一 ThreadLocal  
//                  x.set(data);  
//                  方式二 new对象方式，将多个属性放到对象中  
//                  MyThreadScopeData myData = new MyThreadScopeData();  
//                  myData.setName("name" + data);  
//                  myData.setAge(data);  
//                  myThreadScopeData.set(myData);  
//                  方式三 使用单例模式  
                    MyThreadScopeData.getThreadInstance().setName("name" + data);  
                    MyThreadScopeData.getThreadInstance().setAge(data);  
                      
                    new A().get();  
                    new B().get();  
                }                             
            }).start();  
        }  
    }  
      
    static class A{  
        public void get(){  
//          方式一 ThreadLocal  
//          int data = x.get();  
//          System.out.println("A from " + Thread.currentThread().getName()   
//                  + " get data :" + data);  
//          方式二 new对象方式，将多个属性放到对象中  
//          MyThreadScopeData myData = myThreadScopeData.get();;  
//          System.out.println("A from " + Thread.currentThread().getName()   
//                  + " getMyData: " + myData.getName() + "," +  
//                  myData.getAge());  
//          方式三 使用单例模式  
            MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();  
            System.out.println("A from " + Thread.currentThread().getName()   
                    + " getMyData: " + myData.getName() + "," +  
                    myData.getAge());  
        }  
    }  
      
    static class B{  
        public void get(){  
//          int data = x.get();           
//          System.out.println("B from " + Thread.currentThread().getName()   
//                  + " get data :" + data);  
//          MyThreadScopeData myData = myThreadScopeData.get();;  
//          System.out.println("B from " + Thread.currentThread().getName()   
//                  + " getMyData: " + myData.getName() + "," +  
//                  myData.getAge());  
            MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();  
            System.out.println("B from " + Thread.currentThread().getName()   
                    + " getMyData: " + myData.getName() + "," +  
                    myData.getAge());             
        }         
    }  
}  
  
class MyThreadScopeData{  
      
    private MyThreadScopeData(){}  
      
    private static MyThreadScopeData instance = null;//new MyThreadScopeData();  
      
    private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();  
      
    public static /*synchronized*/ MyThreadScopeData getThreadInstance(){  
        MyThreadScopeData instance = map.get();  
        if(instance == null){  
            instance = new MyThreadScopeData();  
            map.set(instance);  
        }  
        return instance;  
    }  
      
      
    private String name;  
    private int age;  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public int getAge() {  
        return age;  
    }  
    public void setAge(int age) {  
        this.age = age;  
    }  
} 