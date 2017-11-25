package com.wjj.top.dao;

import com.wjj.top.entity.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NewsDao {
    String TABLE_NAME ="news";
    //最好前后加空格，方便拼接
    String INSERT_FILEDS=" title,link,image,like_count,comment_count,created_date,user_id ";
        String SELECT_FILEDS=" id, "+INSERT_FILEDS;

    // # 取对象中数据
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FILEDS,")values(#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);

    List<News> selectByUserAndOffset(@Param("userId") Integer userId,@Param("offset") Integer offset, @Param("limit") Integer limit);

    News selectById(@Param("id") Integer id);

}
