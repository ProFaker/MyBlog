package com.wjj.top.dao;

import com.wjj.top.entity.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BlogDao {


    String TABLE_NAME ="blog";
    //最好前后加空格，方便拼接
    String INSERT_FILEDS="groupId,blogTitle,blogContent,blogSummary ,blogType, blogTag,userId";
    String SELECT_FILEDS=" id, "+INSERT_FILEDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FILEDS,")values(#{groupId},#{blogTitle},#{blogContent},#{blogSummary},#{blogType},#{blogTag},#{userId})"})
    int addBlog(Blog news);

    List<Blog> selectByGroupIdAndOffset(@Param("groupId") Integer groupId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    Blog selectById(@Param("id") Integer id);
}
