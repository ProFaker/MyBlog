package com.wjj.top.async;

import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventModel {

    private  EventType type;
    //触发者
    private  int actorId;
    //触发对象
    private  int entityId;
    private  int entityType;
    //触发对象拥有者
    private  int entityOwnerId;

    //触发事件现场
    private Map<String , String > exts =new HashMap<String ,String>();

    public EventModel(EventType type) {
        this.type = type;
    }

    public EventModel(){

    }

    public EventType getType() {
        return type;
    }


    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
