package com.concurrent.callableAndFuture;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallableAndFuture2 {  
	  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
          
        ExecutorService threadPool2 =  Executors.newFixedThreadPool(10);  
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool2);  
        for(int i=1;i<=10;i++){  
            final int seq = i;  
            completionService.submit(new Callable<Integer>() {  
                @Override  
                public Integer call() throws Exception {  
                    Thread.sleep(new Random().nextInt(5000));  
                    return seq;  
                }  
            });  
        }  
        for(int i=0;i<10;i++){  
            try {  
                System.out.println(  
                        completionService.take().get());  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            } catch (ExecutionException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
      
} 
