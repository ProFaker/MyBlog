package com.wjj.top.controller;

import com.wjj.top.entity.Classify;
import com.wjj.top.service.ClassifyService;
import com.wjj.top.utils.TopUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassifyController {

    private static Logger logger = LoggerFactory.getLogger(ClassifyController.class);

    @Autowired
    ClassifyService classifyService;

    @RequestMapping("/getClassifyByUserId")
    public List<Classify> getClassifyByUserId(@RequestParam("userId") int UserId){

        try {
            List<Classify> classifies= classifyService.getClassifyByUserId(UserId);
            return classifies;

        }catch (Exception e){
            logger.error("获取分类失败："+e.getMessage());
            return null;
        }
    }
    @RequestMapping("/addClassify")
    public String addClassify(@RequestParam("parentId") int parentId,
                              @RequestParam("displayName") String displayName,
                              @RequestParam("userId") int userId){
        Classify classify = new Classify(parentId,displayName,userId);
        try {
            int result = classifyService.addClassify(classify);
            if(result > 0)
                return TopUtils.getJsonString(0,"添加分类成功");
            else
                return TopUtils.getJsonString(1,"添加分类失败");
        }catch (Exception e){
            logger.error("添加分类失败："+e.getMessage());
            return null;
        }
    }
}
