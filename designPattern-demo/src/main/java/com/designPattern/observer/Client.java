package com.designPattern.observer;

public class Client {

    public static void main(String[] args) throws Exception {
        Observable observable = new Observable();
        observable.addObserver(new ConcreteObserver1());
        observable.addObserver(new ConcreteObserver2());
        
        observable.changed();
    }
}
