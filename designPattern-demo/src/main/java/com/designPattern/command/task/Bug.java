package com.designPattern.command.task;

import com.designPattern.command.role.Programmer;

/*
 * 缺陷任务
 * 
*/

public class Bug implements Task{

    private Programmer programmer;
    
    public Bug(Programmer programmer) {
        super();
        this.programmer = programmer;
    }
    
    public void handle() {
        programmer.handleBug();
    }

    public String toString() {
        return "Bug [programmer=" + programmer.getName() + "]";
    }
    
}
