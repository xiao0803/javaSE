package com.designPattern.command;

/*
 * 需求任务
 * 
*/
public class Demand implements Task{

    private Programmer programmer;
    
    public Demand(Programmer programmer) {
        super();
        this.programmer = programmer;
    }
    
    public void handle() {
        programmer.handleDemand();
    }

    public String toString() {
        return "Demand [programmer=" + programmer.getName() + "]";
    }
    
}
