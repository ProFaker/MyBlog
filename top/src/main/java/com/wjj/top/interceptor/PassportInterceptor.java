package com.wjj.top.interceptor;

import com.wjj.top.dao.LoginTicketDao;
import com.wjj.top.dao.UserDao;
import com.wjj.top.entity.HostHolder;
import com.wjj.top.entity.LoginTicket;
import com.wjj.top.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Hashtable;
import java.util.TreeMap;

@Component
public class PassportInterceptor implements HandlerInterceptor
{
    @Autowired
    LoginTicketDao loginTicketDao;

    @Autowired
    UserDao userDao;

    @Autowired
    HostHolder hostHolder;
    @Override
    //请求前触发
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String orgin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", orgin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers"," Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
        response.setHeader("Access-Control-Allow-Methods"," GET, POST, PUT,DELETE");
        String ticket=null;
        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){{
                    if(cookie.getName().equals("ticket")){
                        ticket=cookie.getValue();
                        break;
                    }
            }}
        }

        if(ticket != null){
            LoginTicket loginTicket=loginTicketDao.selectByTicket(ticket);
            if(loginTicket == null || loginTicket.getStatus() == 1){
                return  true;
            }


            User user = userDao.selectById(loginTicketDao.getUserIdByTicket(ticket));
            hostHolder.setUser(user);

        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if(modelAndView != null && hostHolder.getUser() != null){
            //modelAndView 进行前后端交互 方法在渲染之前执行 (前端可以直接用user.name获取用户名)
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        //结束
        hostHolder.clear();
    }
}
