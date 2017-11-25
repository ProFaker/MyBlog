package com.wjj.top.dao;


import com.wjj.top.entity.LoginTicket;
import com.wjj.top.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginTicketDao {

    String TABLE_NAME ="ticket";
    //最好前后加空格，方便拼接
    String INSERT_FILEDS=" user_id,expired,status,ticket ";
    String SELECT_FILEDS=" id, "+INSERT_FILEDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FILEDS,") values(#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    @Select({"select ",SELECT_FILEDS," from ",TABLE_NAME, " where ticket= #{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update ",TABLE_NAME,"set status =#{status} where ticket=#{ticket}"})
    int updateStatus(@Param("ticket") String ticket,@Param("status") int status);

    @Select({"select user_id from ",TABLE_NAME ,"where ticket=#{ticket}"} )
    int getUserIdByTicket(@Param("ticket") String ticket);
}
