package com.designPattern.observer.eventListener;

import java.util.EventObject;

public class WriterEvent extends EventObject{
    
    private static final long serialVersionUID = 8546459078247503692L;

    public WriterEvent(Writer writer) {
        super(writer);
    }
    
    public Writer getWriter(){
        return (Writer) super.getSource();
    }

}
