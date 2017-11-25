package com.wjj.top.service;

import com.wjj.top.dao.NewsDao;
import com.wjj.top.entity.News;
import com.wjj.top.utils.TopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {
    @Autowired
    private NewsDao newsDao;

    public List<News> selectByUserAndOffset(int userId, int offset, int limit) {
        return newsDao.selectByUserAndOffset(userId, offset, limit);
    }

    public News selectById(int id){
        return newsDao.selectById(id);
    }

    public String  saveImage(MultipartFile file , HttpServletRequest request) throws IOException{

        int dotPos=file.getOriginalFilename().lastIndexOf(".");
        if(dotPos < 0)
            return null;

        String fileExt = file.getOriginalFilename().substring(dotPos+1).toLowerCase();
        if(!TopUtils.isFileAllowed(fileExt))
            return  null;
        String fileName= UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;

        //项目真实路径
        String host = TopUtils.getProjectPath(request);

        Files.copy(file.getInputStream(),new File(host+"image"+ fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);

        return  host + fileName;
    }
}
