package com.wjj.top.async;

import java.util.List;

public interface EventHandler {

    void doHandler(EventModel model);
    //handler关注的Event,handler关注的所有事件类型都需要进行处理
    List<EventType> getSupportEventType();

}
