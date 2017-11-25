package com.wjj.top.service;

import com.wjj.top.dao.BlogDao;
import com.wjj.top.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    BlogDao blogDao;

    public int addBlog(Blog blog){
        return blogDao.addBlog(blog);
    }

    public List<Blog> selectByGroupIdAndOffset(int groupId , int offset ,int limit){
        return blogDao.selectByGroupIdAndOffset(groupId,offset,limit);
    }
}
