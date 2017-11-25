package com.wjj.top.entity;

import org.springframework.stereotype.Component;

@Component
public class Blog {

    private int id;
    private int groupId;
    private String blogTitle;
    private String blogContent;
    private String blogSummary;
    private String blogType;
    private String blogTag;
    private int userId;

    public Blog() {
    }

    public Blog(int groupId, String blogTitle, String blogContent, String blogSummary, String blogType, String blogTag,int userId) {
        this.userId = userId;
        this.groupId = groupId;
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.blogSummary = blogSummary;
        this.blogType = blogType;
        this.blogTag = blogTag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }


    public String getBlogType() {
        return blogType;
    }

    public void setBlogType(String blogType) {
        this.blogType = blogType;
    }

    public String getBlogTag() {
        return blogTag;
    }

    public void setBlogTag(String blogTag) {
        this.blogTag = blogTag;
    }

    public String getBlogSummary() {
        return blogSummary;
    }

    public void setBlogSummary(String blogSummary) {
        this.blogSummary = blogSummary;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
