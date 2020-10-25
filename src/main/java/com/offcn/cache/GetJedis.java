package com.offcn.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class GetJedis {
    private static JedisPool jedisPool;
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50);
        config.setMaxIdle(20); // 最大的闲时连接
        config.setMinIdle(5); // 最小的闲时连接
        jedisPool = new JedisPool(config,"localhost",6379);
    }
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

}
