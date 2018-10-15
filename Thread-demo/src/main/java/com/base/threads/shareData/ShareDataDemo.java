package com.base.threads.shareData;

public class ShareDataDemo {  
	  
    private int money;  
    public ShareDataDemo(int money){  
      this.money=money;  
    }  
      
    public synchronized void getMoney(int money){  
     //注意这个地方必须用while循环，因为即便再存入钱也有可能比取的要少  
     while(this.money<money){           
          System.out.println("取款："+money+" 余额："+this.money+" 余额不足，正在等待存款......");  
          try{ wait();} catch(Exception e){}  
     }  
     this.money=this.money-money;  
     System.out.println("取出："+money+" 还剩余："+this.money);  
      
    }  
      
    public synchronized void setMoney(int money){  
      
     try{ Thread.sleep(10);}catch(Exception e){}  
     this.money=this.money+money;  
     System.out.println("新存入："+money+" 共计："+this.money);  
     notify();  
    }  
      
    public static void main(String args[]){  
         ShareDataDemo ShareDataDemo=new ShareDataDemo(0);  
         Bank b=new Bank(ShareDataDemo);  
         Consumer c=new Consumer(ShareDataDemo);  
         new Thread(b).start();  
         new Thread(c).start();  
    }  
}  
//存款类  
class Bank implements Runnable {  
       ShareDataDemo ShareDataDemo;  
       public Bank(ShareDataDemo ShareDataDemo){  
           this.ShareDataDemo=ShareDataDemo;  
       }  
       public void run(){  
           while(true){  
                int temp=(int)(Math.random()*1000);  
                ShareDataDemo.setMoney(temp);    
   }  
}  
 
}  
//取款类  
class Consumer implements Runnable {  
       ShareDataDemo ShareDataDemo;  
       public Consumer(ShareDataDemo ShareDataDemo){  
           this.ShareDataDemo=ShareDataDemo;  
       }  
       public void run(){  
       while(true){           
           int temp=(int)(Math.random()*1000);  
           ShareDataDemo.getMoney(temp);  
       }  
   }  
} 