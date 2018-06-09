package com.wjj.top.service;

import com.mysql.cj.core.util.StringUtils;
import com.wjj.top.dao.LoginTicketDao;
import com.wjj.top.dao.UserDao;
import com.wjj.top.entity.LoginTicket;
import com.wjj.top.entity.User;
import com.wjj.top.utils.TopUtils;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginTicketDao loginTicketDao;

    public Map<String ,Object> login(String userName, String password){
        Map<String,Object> map=new HashMap<String,Object>();
        if(StringUtils.isNullOrEmpty(userName)){
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isNullOrEmpty(password)){
            map.put("msgpwd","密码不能为空");
            return map;
        }

        User user=userDao.selectByUserName(userName);

        if(user == null){
            map.put("msgname","该用户不存在");
            return map;
        }

        if(!user.getPassword().equals((password))){
            map.put("msgpwd","密码不正确");
            return map;
        }

        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }

    public String addLoginTicket(int userId){
        LoginTicket ticket=new LoginTicket();
        ticket.setUserId(userId);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        Date date=new Date();
        date.setTime(date.getTime()+3600*5*24);
        ticket.setExpired(date);
        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }
    public void logout(String ticket){
        loginTicketDao.updateStatus(ticket,1);
    }
}
