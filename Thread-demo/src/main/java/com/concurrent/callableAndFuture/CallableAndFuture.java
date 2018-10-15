package com.concurrent.callableAndFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {  
	  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        //单一线程的线程池  
        ExecutorService threadPool =  Executors.newSingleThreadExecutor();  
        Future<String> future =  
            threadPool.submit(  
                new Callable<String>() {  
                    public String call() throws Exception {  
                        Thread.sleep(2000);  
                        return "hello";  
                    };  
                }  
        );  
        System.out.println("等待结果");  
        try {  
            System.out.println("拿到结果" + future.get());  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
    }  
      
  
}