package com.offcn.cache;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JedisForCount {
    //创建设定缓存数据要保存的主题
    private static String topic = "RollCallor";
    private static String seats = "seats";
    public static String readCount(String realName){
        Jedis jedis = GetJedis.getJedis();
        String record = jedis.hget(topic, realName);
        jedis.close();
        return record;
    }

    public static Long writeCount(String realName,String record){
        Jedis jedis = GetJedis.getJedis();
        Long res = jedis.hset(topic, realName, record);
        jedis.close();
        return res;
    }

    public static Long writeSeat(String seatIndex,String name){
        Jedis jedis = GetJedis.getJedis();
        Long res = jedis.hset(seats, seatIndex, name);
        jedis.close();
        return res;
    }

    public static String readSeat(String seatIndex){
        Jedis jedis = GetJedis.getJedis();
        String record = jedis.hget(seats, seatIndex);
        jedis.close();
        return record;
    }

    //获取所有的座位编号和对应的学员
    public static Map<Integer,String> getSeatIndex(){
        Map<Integer,String> map = new HashMap<>();
        Jedis jedis = GetJedis.getJedis();
        List<String> hvals = jedis.hvals(seats);
        for (String hval : hvals) {
            String[] split = hval.split("=", -1);
            int index = Integer.parseInt(split[0]);
            map.put(index,split[1]);
        }

        return map;
    }

    public static Long clear_allseats(){
        Jedis jedis = GetJedis.getJedis();
        return jedis.del(seats);
    }

    public static Long clear_one(int index){
        Jedis jedis  = GetJedis.getJedis();
        Long hdel = jedis.hdel(seats, index + "");
        return hdel;
    }
}
