package com.wjj.top.controller;

import com.wjj.top.service.NewsService;
import com.wjj.top.utils.TopUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@RestController
public class NewsController {

    public static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    NewsService newsService;


    @RequestMapping(value = "/uploadImage" , method = RequestMethod.POST)
    public String uploadImage(@RequestParam("file")MultipartFile file , HttpServletRequest request){
        try{
            String fileUrl = newsService.saveImage(file ,request);
            if(fileUrl == null)
                return TopUtils.getJsonString(1,"上传图片失败");

            return TopUtils.getJsonString(0,fileUrl);

        }catch (Exception e){
            logger.error("上传文件失败"+e.getMessage());
            return TopUtils.getJsonString(1,"上传图片失败");
        }
    }

    @RequestMapping(value = "/getIamge")
    public String getImage(@RequestParam("name") String name , HttpServletResponse response ,HttpServletRequest request){

        String host = request.getSession().getServletContext().getRealPath("/");
        try {
            StreamUtils.copy(new FileInputStream(new File(TopUtils.getProjectPath(request)+name)),response.getOutputStream());
            return TopUtils.getJsonString(0,"获取图片成功");
        } catch (IOException e) {

            logger.error("获取图片失败"+e.getMessage());
            return  TopUtils.getJsonString(0, "获取图片错误");
        }
    }


}
