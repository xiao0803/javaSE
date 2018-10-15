package com.designPattern.singleton;

public class InnerClassHungrySingleton {
    
    private InnerClassHungrySingleton(){}
    public static InnerClassHungrySingleton getInstance(){
        return SingletonInstance.instance;
    }
    
    private static class SingletonInstance{
        static InnerClassHungrySingleton instance = new InnerClassHungrySingleton(); 
    }
}

//与上面的作用是一样的
/*public class Singleton {
    
    private static Singleton singleton = new Singleton();//此处编译后和上面的静态内部类编译后的结果一样
    private Singleton(){}
    public static Singleton getInstance(){
        return singleton;
    }
}*/