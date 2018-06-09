package com.wjj.top.entity;

import org.springframework.stereotype.Component;

@Component
public class Classify {

    private int id;
    private int parentId;
    private String displayName;
    private int userId;

    public Classify() {
    }

    public Classify(int parentId, String displayName,int userId) {
        this.parentId = parentId;
        this.displayName = displayName;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
