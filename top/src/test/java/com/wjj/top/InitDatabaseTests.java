package com.wjj.top;

import com.wjj.top.dao.LoginTicketDao;
import com.wjj.top.dao.NewsDao;
import com.wjj.top.dao.UserDao;
import com.wjj.top.entity.LoginTicket;
import com.wjj.top.entity.News;
import com.wjj.top.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Sql("xxx.sql") 启动之前导入数据库文件
public class InitDatabaseTests {

	@Autowired
	private UserDao userDao;

	@Autowired
	private NewsDao newsDao;

	@Autowired
	private LoginTicketDao loginTicketDao;

	@Test
	public void contextLoads() {
		Random random=new Random();
		for(int i=0;i<10;i++){
			User user=new User();
			user.setName("UserName"+i);
			user.setPassword("");
			user.setSalt("");
			user.setHead_url(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
			userDao.addUser(user);

			News news=new News();
			news.setCommentCount(i+1);
			Date date=new Date();
			date.setTime(date.getTime()+1000*3600*5*i);
			news.setCreatedDate(date);
			news.setImage(String.format("http://images.nowcoder.com/head/%dm.png",random.nextInt(1000)));
			news.setLikeCount((i+1)*200);
			news.setLink(String.format("http://www.newcoder.com/%d.html",i));
			news.setTitle(String.format("TITLE%d",i+1));
			news.setUserId(i+1);
			newsDao.addNews(news);

			LoginTicket ticket=new LoginTicket();
			ticket.setStatus(0);
			ticket.setUserId(i+1);
			ticket.setTicket(String.format("TIKET%d",i+1));
			ticket.setExpired(date);
			loginTicketDao.addTicket(ticket);
		}
	}

}
