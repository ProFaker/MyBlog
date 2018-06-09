package com.wjj.top.controller;

import com.wjj.top.entity.Blog;
import com.wjj.top.entity.HostHolder;
import com.wjj.top.service.BlogService;
import com.wjj.top.service.ClassifyService;
import com.wjj.top.service.lucene.CSearchConfig;
import com.wjj.top.service.lucene.CSearchDocument;
import com.wjj.top.utils.ElasticSearchUtils;
import com.wjj.top.utils.FileUtils;
import com.wjj.top.utils.TopUtils;
import com.wjj.top.service.lucene.CSearchTest;
import com.wjj.top.service.lucene.CSearchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@RestController
public class BlogController {

    private static Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    BlogService blogService;

    ElasticSearchUtils elasticSearchUtils = null;

    @Autowired
    CSearchTest cSearchTest;

    @Autowired
    ClassifyService classifyService;

    @Autowired
    private CSearchConfig cSearchConfig;

    @RequestMapping("/testLucene")
    public void testLucene() throws Exception {
        CSearchUtils.createIndex(cSearchConfig);
        CSearchUtils.search(cSearchConfig,"函数",1);
    }

    @RequestMapping(value = "/addBlog")
    public String addBlog(@RequestParam("groupId") int groupId,
                          @RequestParam("blogTitle") String  blogTitle,
                          @RequestParam("blogContent") String blogContent,
                          @RequestParam("blogSummary") String blogSummary,
                          @RequestParam("blogTag") String blogTag,
                          @RequestParam("blogTag") String blogType,
                          HttpServletRequest request){
        int userId = hostHolder.getUser().getId();
        Blog blog = new Blog(groupId,blogTitle,blogContent,blogSummary ,blogType,blogTag,userId);

        try{
//            ResponseEntity responseEntity = elasticSearchUtils.add(blog);
//            System.out.println(responseEntity);
            //将文章存到文件服务器中
            String contentUrl=FileUtils.writeToFile(blogContent,blogTitle,request);
            System.out.println(contentUrl);
            blog.setBlogContent(contentUrl);
            int result = blogService.addBlog(blog);

            //创建索引
            String ID = String.valueOf(blogTitle.hashCode());
            String title = blogTitle;
            String path = contentUrl;
            CSearchDocument document = new CSearchDocument(ID,title,path);


            CSearchUtils.createIndex(cSearchConfig,document);

            if(result > 0)
                return TopUtils.getJsonString(0,"添加文章成功");
            else
                return TopUtils.getJsonString(1,"添加文章失败");

        }catch (Exception e){
            logger.error("添加文章失败："+e.getMessage());
            return TopUtils.getJsonString(1,"添加文章失败");
        }
    }

    @RequestMapping("/query/blog/article")
    @ResponseBody
    public List<CSearchDocument> query(@RequestParam(value = "content",defaultValue = "")String content) throws Exception {
        List<CSearchDocument> result = CSearchUtils.search(cSearchConfig,content,10);
        return  result;
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

    @RequestMapping(value = "/getClassifyCount")
    public String getClassifyCount(@RequestParam("groupId") int groupId){
        try{
            int count = blogService.getClassifyCount(groupId);
            return  TopUtils.getJsonString(0,String.valueOf(count));
        }catch (Exception e){
            logger.error("查询错误",e.getMessage());
            return TopUtils.getJsonString(1,"查询错误");
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

    @RequestMapping(value = "/readFileByPath")
    public String readFileByPath(@RequestParam("path") String path){
        String content = FileUtils.readFile(path);
        return  TopUtils.getJsonString(0,content);
    }
}
