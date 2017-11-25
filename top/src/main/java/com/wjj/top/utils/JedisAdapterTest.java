package com.wjj.top.utils;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class JedisAdapterTest {

    public static void print(int index ,Object value){

        System.out.println(String.format("%d  :  %s",index,value.toString()));
    }

    public static void main(String[] args){

        Jedis jedis = new Jedis();
        //删除所有
        jedis.flushAll();
        jedis.set("pw","100");
        print(1,jedis.get("pw"));

        jedis.rename("pw","pv");
        //设置过期时间
        jedis.setex("hello",15,"world");


        //数值加1
        jedis.incr("pv");
        print(2,jedis.get("pv"));
        //指定加数
        jedis.incrBy("pv",5);
        print(2,jedis.get("pv"));

        //列表操作
        String listName="listA";
        for(int i=0;i<10;++i){
            jedis.lpush(listName,"a"+ String.valueOf(i));
        }

        print(3,jedis.lrange(listName,0,9));
        print(4,jedis.llen(listName));
        print(5,jedis.lpop(listName));
        print(6,jedis.llen(listName));
        print(7,jedis.lindex(listName,3));
        jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER,"a4","xx");

        String userKey = "user";
        jedis.hset(userKey , "name" , "jim");
        jedis.hset(userKey , "age" , "12");
        jedis.hset(userKey , "phone" , "15800683715");

        print(8 , jedis.hget(userKey , "name"));
        jedis.hdel(userKey , "phone");
        print(9 , jedis.hgetAll(userKey));

        //hashset所有的  key value
        print(10 , jedis.hkeys(userKey));

        //不存在字段则添加
        jedis.hsetnx(userKey , "school" ,"xx");

        //set 集合
        String likeKeys1 = "newLike1";
        String likeKeys2 = "newLike2";
        for(int i=0;i<10;i++){
            jedis.sadd(likeKeys1 , String.valueOf(i));
            jedis.sadd(likeKeys2 , String.valueOf(i*2));
        }
        print(11,jedis.smembers(likeKeys1));
        print(12,jedis.smembers(likeKeys2));
        //集合的交并补
        print(13,jedis.sinter(likeKeys1,likeKeys2));
        print(14,jedis.sunion(likeKeys1,likeKeys2));
        print(15,jedis.sdiff(likeKeys1,likeKeys2));
        //删除元素
        jedis.srem(likeKeys1,"5");
        //把14从likekey1移动到likekey2
        jedis.smove(likeKeys1,likeKeys2,"14");
        //集合元素个数
        jedis.scard(likeKeys1);

        //优先队列
        String rankKey = "rankKey";
        jedis.zadd(rankKey,15,"jim");
        jedis.zadd(rankKey,60,"Nam");
        jedis.zadd(rankKey,75,"Lucy");
        jedis.zadd(rankKey,90,"Tom");

        jedis.zcard(rankKey);
        jedis.zcount(rankKey,61,100);
        jedis.zscore(rankKey,"Lucy");
        jedis.zincrby(rankKey,2,"Lucy");

        //打印1-3名
        print(16,jedis.zrange(rankKey,1,3));
        //打印倒数1-3名
        print(17,jedis.zrevrange(rankKey,1,3));

        for(Tuple tuple : jedis.zrangeByScoreWithScores(rankKey,"0","100")){
            print(18,tuple.getElement() + ":" + String.valueOf(tuple.getScore()) );

        }

        //查看排名
        print(19,jedis.zrank(rankKey,"Lucy"));
    }
}
