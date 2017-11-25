package com.wjj.top.async.handler;

import com.wjj.top.async.EventHandler;
import com.wjj.top.async.EventModel;
import com.wjj.top.async.EventType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LoginHandler implements EventHandler {


    @Override
    public void doHandler(EventModel model) {
        System.out.println("Like hanler");
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LOGIN);
    }


}
