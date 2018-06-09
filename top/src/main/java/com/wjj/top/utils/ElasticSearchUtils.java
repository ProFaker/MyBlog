package com.wjj.top.utils;

import com.wjj.top.entity.Blog;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;

public class ElasticSearchUtils {
    Logger logger = LoggerFactory.getLogger(ElasticSearchUtils.class);

    @Autowired
    private  TransportClient client;

    public  ResponseEntity add(Blog blog){

        try{
            Date date = new Date();
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("blogTitle",blog.getBlogTitle())
                    .field("groupId",blog.getGroupId())
                    .field("blogContent",blog.getBlogContent())
                    .field("blogType",blog.getBlogType())
                    .field("blogTag",blog.getBlogType())
                    .endObject();
            IndexResponse result = this.client.prepareIndex("blog","article")
                    .setSource(content)
                    .get();
            return new ResponseEntity(result, HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
