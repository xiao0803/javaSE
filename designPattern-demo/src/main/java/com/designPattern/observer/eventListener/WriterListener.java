package com.designPattern.observer.eventListener;

import java.util.EventListener;

public interface WriterListener extends EventListener{

    void addNovel(WriterEvent writerEvent);
    
}