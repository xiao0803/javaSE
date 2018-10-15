package com.base.threads.shareData;

/** 
 * 多线程共享数据-卖票系统 
 * @author hejingyuan 
 * 
 */  
public class OneRunnable {  
  
     /**  
     * @param args  
     */    
    public static void main(String[] args) {    
        Ticket t = new Ticket();    
        new Thread(t).start();    
        new Thread(t).start();    
    }    
}  
class Ticket implements Runnable{    
        
    private int ticket = 10;    
    public void run() {    
        while(ticket>0){    
            ticket--;    
            System.out.println("当前票数为："+ticket);    
        }    
            
    }    
    
}