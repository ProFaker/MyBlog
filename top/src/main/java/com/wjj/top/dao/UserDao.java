package com.wjj.top.dao;

import com.wjj.top.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    String TABLE_NAME ="user";
    //最好前后加空格，方便拼接
    String INSERT_FILEDS=" name,password,salt,head_url ";
    String SELECT_FILEDS=" id,name,password,salt,head_url ";

    // # 去对象中数据
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FILEDS,")values(#{name},#{password},#{salt},#{head_url})"})
    int addUser(User user);

    @Select({"select ",SELECT_FILEDS," from ",TABLE_NAME, " where name= #{name}"})
    User selectByUserName(String name);

    @Select({"select ",SELECT_FILEDS," from ",TABLE_NAME, " where name= #{userId}"})
    User selectById(@Param("userId") int userId);

}
