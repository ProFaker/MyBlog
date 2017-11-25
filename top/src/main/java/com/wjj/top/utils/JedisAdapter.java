package com.wjj.top.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service
//相当于dao层
public class JedisAdapter implements InitializingBean{

    private  static Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private  static JedisPool pool = null;


    //存对象
    public void setObject(String key , Object obj){

        set(key , JSON.toJSONString(obj));
    }

    //取对象
    public <T> T getObject(String key , Class<T> clazz){

        String value = get(key);
        if(value != null){
            return JSON.parseObject(value ,clazz);
        }
        return  null;
    }

    public long s(String key , String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return  jedis.sadd(key,value);

        }catch (Exception e){
            logger.error("添加失败：",e.getMessage());
            return 0;
        }finally {
            if(jedis != null){
                //释放jedis 连接池
                jedis.close();
            }
        }
    }

    public long srem(String key , String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return  jedis.srem(key,value);

        }catch (Exception e){
            logger.error("删除失败：",e.getMessage());
            return 0;
        }finally {
            if(jedis != null){
                //释放jedis 连接池
                jedis.close();
            }
        }
    }

    public boolean sismember(String key , String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return  jedis.sismember(key,value);

        }catch (Exception e){
            logger.error("添加失败：",e.getMessage());
            return false;
        }finally {
            if(jedis != null){
                //释放jedis 连接池
                jedis.close();
            }
        }
    }

    public long scard(String key){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return  jedis.scard(key);

        }catch (Exception e){
            logger.error("查询失败：",e.getMessage());
            return 0;
        }finally {
            if(jedis != null){
                //释放jedis 连接池
                jedis.close();
            }
        }
    }

    public long lpush(String key ,String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return  jedis.lpush(key,value);

        }catch (Exception e){
            logger.error("查询失败：",e.getMessage());
            return 0;
        }finally {
            if(jedis != null){
                //释放jedis 连接池
                jedis.close();
            }
        }
    }
    public List<String> brpop(int timeout,String key){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return  jedis.brpop(timeout,key);

        }catch (Exception e){
            logger.error("查询失败：",e.getMessage());
            return null;
        }finally {
            if(jedis != null){
                //释放jedis 连接池
                jedis.close();
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("localhost",6379);
    }

    private Jedis  getJedis(){
        return  pool.getResource();
    }

    private void set(String key ,String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.set(key,value);
        }catch (Exception e){
            logger.error("添加失败：",e.getMessage());
        }finally {
            jedis.close();;
        }
    }

    private  String get(String key){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.get(key);
        }catch (Exception e){
            logger.error("获取失败：",e.getMessage());
            return null;
        }finally {
            jedis.close();;
        }
    }

}
