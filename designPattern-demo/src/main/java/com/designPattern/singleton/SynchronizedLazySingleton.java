package com.designPattern.singleton;

/*
 * 双重加锁（方式1）
*/
public class SynchronizedLazySingleton {

    //一个静态的实例
    private volatile static SynchronizedLazySingleton synchronizedLazySingleton;
    //私有化构造函数
    private SynchronizedLazySingleton(){}
    //给出一个公共的静态方法返回一个单一实例
    public static SynchronizedLazySingleton getInstance(){
        if (synchronizedLazySingleton == null) {
            synchronized (SynchronizedLazySingleton.class) {
                if (synchronizedLazySingleton == null) {
                    synchronizedLazySingleton = new SynchronizedLazySingleton();
                }
            }
        }
        return synchronizedLazySingleton;
    }
}

/*
使用volatile关键字的作用说明

首先要明白在JVM创建新的对象时，主要要经过三步
1.分配内存 2.初始化构造器 3.将对象指向分配的内存的地址

这种顺序在上述双重加锁的方式是没有问题的，因为这种情况下JVM是完成了整个对象的构造才将内存的地址交给了对象。
但是如果2和3步骤是相反的（2和3可能是相反的是因为JVM会针对字节码进行调优，而其中的一项调优便是调整指令的执行顺序），就会出现问题了。
因为这时将会先将内存地址赋给对象，针对上述的双重加锁，就是说先将分配好的内存地址指给synchronizedSingleton，
然后再进行初始化构造器，这时候后面的线程去请求getInstance方法时，会认为synchronizedSingleton对象已经实例化了，
直接返回一个引用。如果在初始化构造器之前，这个线程使用了synchronizedSingleton，就会产生莫名的错误。

*/