package com.wjj.top.controller;

import com.wjj.top.entity.Blog;
import com.wjj.top.entity.HostHolder;
import com.wjj.top.service.BlogService;
import com.wjj.top.utils.TopUtils;
import org.apache.catalina.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BlogController {

    private static Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    BlogService blogService;

    @RequestMapping(value = "/addBlog")
    public String addBlog(@RequestParam("groupId") int groupId,
                          @RequestParam("blogTitle") String  blogTitle,
                          @RequestParam("blogContent") String blogContent,
                          @RequestParam("blogSummary") String blogSummary,
                          @RequestParam("blogTag") String blogTag,
                          @RequestParam("blogTag") String blogType){
        int userId = hostHolder.getUser().getId();
        Blog blog = new Blog(groupId,blogTitle,blogContent,blogSummary ,blogType,blogTag,userId);

        try{
            int result = blogService.addBlog(blog);
            if(result > 0)
                return TopUtils.getJsonString(0,"添加文章成功");
            else
                return TopUtils.getJsonString(1,"添加文章失败");

        }catch (Exception e){
            logger.error("添加文章失败："+e.getMessage());
            return TopUtils.getJsonString(1,"添加文章失败");
        }
    }

    @RequestMapping(value = "/getArticleByGroupId")
    public List<Blog> selectByGroupIdAndOffset(@RequestParam("groupId") int groupId ,
                                               @RequestParam("offset") int offset ,
                                               @RequestParam("limit") int limit){
        try{
            List<Blog> result = blogService.selectByGroupIdAndOffset(groupId,offset,limit);
            return  result;

        }catch (Exception e){
            logger.error("查询失败："+e.getMessage());
            return  null;
        }
    }

    @RequestMapping(value = "/getBlogById")
    public Blog getBlogById(@RequestParam("id") int id){
        try {
            Blog blog = blogService.selectById(id);
            if(blog == null){
                logger.error("id错误");
                return null;
            }
            return blog;
        }catch (Exception e){
            logger.error("查询错误"+e.getMessage());
            return  null;
        }
    }
}
