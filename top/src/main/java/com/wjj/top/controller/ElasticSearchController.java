package com.wjj.top.controller;

import com.wjj.top.service.lucene.CSearchDocument;
import com.wjj.top.service.lucene.CSearchUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

public class ElasticSearchController {

    @Autowired
    private TransportClient client;



    @GetMapping("/get/blog/article")
    public ResponseEntity get(@RequestParam(name="id",defaultValue = "") String id ){
        GetResponse  result = this.client.prepareGet("people","man",id).get();
        if(! result.isExists()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(result.getSource(),HttpStatus.OK);
    }

    @RequestMapping("/add/blog/article")
    public ResponseEntity add(@RequestParam("blogTitle") String blogTitle,
                              @RequestParam("groupId") String GroupId,
                              @RequestParam("blogContent") String blogContent,
                              @RequestParam("blogType") String blogType,
                              @RequestParam("blogTag") String blogTag){

        try{
            Date date = new Date("yyyy-MM-dd");
            int groupId = Integer.valueOf(GroupId);
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("blogTitle",blogTitle)
                    .field("groupId",groupId)
                    .field("blogContent",blogContent)
                    .field("blogType",blogType)
                    .field("blogTag",blogTag)
                    .endObject();
            IndexResponse result = this.client.prepareIndex("blog","article")
                    .setSource(content)
                    .get();
            return new ResponseEntity(result,HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/blog/article")
    public ResponseEntity delete(@RequestParam(name = "id")String id){
        DeleteResponse result = this.client.prepareDelete("people","man",id)
                .get();
        return new ResponseEntity(result.getResult().toString(),HttpStatus.OK);
    }

    @PutMapping("/update/blog/article")
    public ResponseEntity updae(@RequestParam(value = "name",required = false)String name,
                                @RequestParam(value = "country",required = false)String country,
                                @RequestParam(value = "age",required = false)String age,
                                @RequestParam(value = "date",required = false)String date,
                                @RequestParam("id")String id){

        UpdateRequest update = new UpdateRequest("people","man",id);
        try{

            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            if(name != null){
                builder.field("name",name);
            }
            if(country != null){
                builder.field("country",country);
            }
            if(age != null){
                builder.field("age",age);
            }
            if(date != null){
                builder.field("date",date);
            }
            builder.endObject();
            update.doc(builder);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try{
            UpdateResponse result = this.client.update(update).get();
            return new ResponseEntity(result.getResult().toString(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//    @PostMapping("/query/blog/article")
    public ResponseEntity query1(@RequestParam(value = "content",defaultValue = "")String content){
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        String blogContent1 = "*"+content+"*";
        WildcardQueryBuilder queryBuilder1 = QueryBuilders.wildcardQuery("blogContent",blogContent1);
//        String blogContent2 = blogContent;
//        WildcardQueryBuilder queryBuilder2 = QueryBuilders.wildcardQuery("blogContent",blogContent2);
//        if(blogContent != null){
//            boolQuery.must(QueryBuilders.matchQuery("blogContent",blogContent));
//        }


        //范围查询
//        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("")
//                .from(0).to(100);
//
//        boolQuery.filter(rangeQuery);

        //模糊查询
//        WildcardQueryBuilder queryBuilder1 = QueryBuilders.wildcardQuery(
//                "name", "*jack*");//搜索名字中含有jack的文档
//        WildcardQueryBuilder queryBuilder2 = QueryBuilders.wildcardQuery(
//                "interest", "*read*");//搜索interest中含有read的文档

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //name中必须含有jack,interest中必须含有read,相当于and
        boolQuery.should(queryBuilder1);
        boolQuery.should(QueryBuilders.matchQuery("blogContent",content));

        SearchRequestBuilder builder = this.client.prepareSearch("blog")
                    .setTypes("article")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQuery)
                .setFrom(0)
                .setSize(10);
        SearchResponse response = builder.get();

        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();

        for(SearchHit hit : response.getHits()){
            result.add(hit.getSource());
        }
        return new ResponseEntity(result,HttpStatus.OK);
    }
}
