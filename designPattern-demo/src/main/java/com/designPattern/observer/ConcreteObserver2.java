package com.designPattern.observer;

public class ConcreteObserver2 implements Observer{

    public void update(Observable o) {
        System.out.println("观察者2观察到" + o.getClass().getSimpleName() + "发生变化");
        System.out.println("观察者2做出相应");
    }

}