package com.base;

/*
实现效果：子线程循环10次，接着主线程循环100次，又回到子线程循环10次，接着再回到主线程又循环100次，如此循环50次

*/

public class ThreadWaitAndNotify {  
	  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
          
        final Business business = new Business();  
        //创建了一个线程，并启动  
        new Thread(  
                new Runnable() {  
                      
                    @Override  
                    public void run() {  
                      
                        for(int i=1;i<=50;i++){  
                            //business的子函数  
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
  
}  
  class Business {  
        
      private boolean bShouldSub = true;  
      //互斥对象为business，即在同一时刻只能访问sub或main其中一个方法  
      public synchronized void sub(int i){  
          //当bShouldSub==false时等待  
          while(!bShouldSub){  
              try {  
                //方法使当前线程主动释放互斥锁，并进入该互斥锁的等待队列。（也就是说，它使当前线程暂停执行，  
                //等待其他线程执行notify（）方法或者notifyall（）方法后再继续执行本线程。）  
                this.wait();  
            } catch (InterruptedException e) {                
                e.printStackTrace();  
            }  
          }  
          for(int j=1;j<=10;j++){  
                System.out.println("sub thread sequence of " + j + ",loop of " + i);  
          }  
          bShouldSub = false;  
          //this代表什么？--代表Business  
          //唤醒下一个线程  
          //唤醒在此对象监视器上等待的单个线程。如果所有线程都在此对象上等待，则会选择唤醒其中一个线程。  
          //选择是任意性的，并在对实现做出决定时发生。  
          this.notify();  
      }  
        
      public synchronized void main(int i){  
            //当bShouldSub==true时等待  
            while(bShouldSub){  
                try {  
                    this.wait();  
                } catch (InterruptedException e) {                    
                    e.printStackTrace();  
                }  
            }  
            for(int j=1;j<=100;j++){  
                System.out.println("main thread sequence of " + j + ",loop of " + i);  
            }  
            bShouldSub = true;  
            this.notify();  
      }  
  }