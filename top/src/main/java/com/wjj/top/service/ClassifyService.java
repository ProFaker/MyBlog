package com.wjj.top.service;

import com.wjj.top.dao.ClassifyDao;
import com.wjj.top.entity.Classify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassifyService {

    @Autowired
    ClassifyDao classifyDao;

    public int addClassify(Classify classify){
        return classifyDao.addClassify(classify);
    }

    public List<Classify> getClassifyByUserId(int userId){
        return classifyDao.getClassifyByUserId(userId);
    }


}
