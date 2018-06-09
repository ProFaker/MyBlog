package com.wjj.top.controller;

import com.wjj.top.async.EventModel;
import com.wjj.top.async.EventProducer;
import com.wjj.top.async.EventType;
import com.wjj.top.entity.HostHolder;
import com.wjj.top.entity.News;
import com.wjj.top.service.NewsService;
import com.wjj.top.service.UserService;
import com.wjj.top.utils.TopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class HomeController {
     @Autowired
     private NewsService newsService;

     @Autowired
     private UserService userService;

     @Autowired
     HostHolder hostHolder;

     @Autowired
     EventProducer eventProducer;

     @RequestMapping(path = "/getNews")
     public List<News> getNews(){
          return  newsService.selectByUserAndOffset(0,0,10);
     }

     @RequestMapping("/getOneItem")
     public News getOneItem(){
          return newsService.selectById(1);
     }

     @RequestMapping(value = "/login")
     public String login(Model model,HttpServletRequest request,
                         @RequestParam("password") String password,
                         @RequestParam("userName") String userName,
                         @RequestParam("remember") String remember,
                         HttpServletResponse response){

          int remember1=Integer.parseInt(remember);
          System.out.print(password);
          Map map=userService.login(userName,password);
          if(map.containsKey("ticket")){
               Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
               cookie.setPath("/");  //全站有效
               if(remember1>0)
                    cookie.setMaxAge(3600*24*5);
               response.addCookie(cookie);
               eventProducer.fireEvent(new EventModel(EventType.LOGIN).setActorId(1)
               .setEntityId(2));
               return TopUtils.getJsonString(0,map.get("ticket").toString());
          }else{
               return TopUtils.getJsonString(1,map);
          }
     }

     @RequestMapping(value = "/logout")
     public String logout(@CookieValue("ticket")String ticket){
          userService.logout(ticket);
          //跳转到首页
          return "index";
     }

     @RequestMapping("/index")
     public String index(HttpServletRequest request){
          request.setAttribute("basePath",TopUtils.getProjectPath(request));
          return "index";
     }

}
