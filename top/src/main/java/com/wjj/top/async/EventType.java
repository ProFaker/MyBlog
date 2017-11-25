package com.wjj.top.async;

//事件类型
public enum  EventType {

    LIKE(0),
    CMMENT(1),
    LOGIN(2),
    MAIL(3);


    private int vlaue;
    EventType(int vlaue){
        this.vlaue = vlaue;
    }
    public int getVlaue() {
        return vlaue;
    }
}
