package com.wjj.top.aspect.configuration;

import com.wjj.top.interceptor.LoginRequiredInterceptor;
import com.wjj.top.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class TopWebConfiguration extends WebMvcConfigurerAdapter{

    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册拦截器
        registry.addInterceptor(passportInterceptor);
        //访问  /login 时触发
//        registry.addInterceptor(passportInterceptor).addPathPatterns("/login*");
        super.addInterceptors(registry);

    }
}
