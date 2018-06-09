package com.wjj.top.async;
import java.util.concurrent.*;
import com.alibaba.fastjson.JSON;
import com.wjj.top.utils.JedisAdapter;
import com.wjj.top.utils.RedisKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements InitializingBean , ApplicationContextAware{

    private static Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private ApplicationContext applicationContext;
    private Map<EventType ,List<EventHandler>> config = new HashMap<EventType, List<EventHandler>>();

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {

        //找到所有实现EventHandler的bean
        Map<String,EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);

        if(beans != null){
            for(Map.Entry<String , EventHandler> entry : beans.entrySet()){
                List<EventType> eventTypes = entry.getValue().getSupportEventType();
                for(EventType type : eventTypes){
                    if(! config.containsKey(type)){
                        config.put(type ,new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    String key = RedisKeyUtils.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0,key);
                    for(String event : events){
                        if(event.equals(key))
                            continue;
                        EventModel model = JSON.parseObject(event,EventModel.class);
                        if(! config.containsKey(model.getType()))
                            logger.error("不能识别事件");
                        for(EventHandler handler : config.get(model.getType()))
                            handler.doHandler(model);
                    }
                }
            }
        });
        thread.start();
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }
}
