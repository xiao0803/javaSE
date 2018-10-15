package com.designPattern.singleton;

public class HungrySingleton {
    
    private static HungrySingleton hungrySingleton = new HungrySingleton();
    
    private HungrySingleton(){}
    
    public static HungrySingleton getInstance(){
        return hungrySingleton;
    }
    
}