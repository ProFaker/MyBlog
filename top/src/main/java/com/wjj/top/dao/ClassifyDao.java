package com.wjj.top.dao;

import com.wjj.top.entity.Classify;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ClassifyDao {

    String TABLE_NAME ="classify";
    //最好前后加空格，方便拼接
    String INSERT_FILEDS=" parentId,displayName ,userId";
    String SELECT_FILEDS=" id, "+INSERT_FILEDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FILEDS,")values(#{parentId},#{displayName},#{userId})"})
    int addClassify(Classify classify);

    List<Classify> getClassifyByUserId(@Param("userId") Integer userId);

}
