package com.wjj.top;

import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil;
import com.wjj.top.dao.LoginTicketDao;
import com.wjj.top.dao.NewsDao;
import com.wjj.top.dao.UserDao;
import com.wjj.top.entity.LoginTicket;
import com.wjj.top.entity.News;
import com.wjj.top.entity.User;
import com.wjj.top.utils.JedisAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Sql("xxx.sql") 启动之前导入数据库文件
public class JedisTest {

	@Autowired
	User user;

	@Autowired
	JedisAdapter jedisAdapter;

	@Test
	public void testObject(){

		user.setId(1);
		user.setHead_url("www.baidu.com");
		user.setSalt("1123");
		user.setPassword("12344");
		user.setName("hello world");

		jedisAdapter.setObject("objTest" , user);

		User u = jedisAdapter.getObject("objTest" , User.class);
		System.out.print(JSON.toJSONString(u));
	}

}
