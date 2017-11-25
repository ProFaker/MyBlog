package com.wjj.top.async;

import com.alibaba.fastjson.JSONObject;
import com.wjj.top.utils.JedisAdapter;
import com.wjj.top.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
 *  将事件放入异步队列中
 */
@Service
public class EventProducer {

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel model){
        try{
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtils.getEventQueueKey();
            jedisAdapter.lpush(key , json);
            return  true;
        }catch (Exception e){
            return false;
        }
    }
}
