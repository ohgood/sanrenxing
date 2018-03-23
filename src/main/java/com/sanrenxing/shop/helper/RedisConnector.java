package com.sanrenxing.shop.helper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis
 * Created on 2017/8/11.
 * @author tony
 */
public class RedisConnector {

    private JedisPool jedisPool;

    public RedisConnector(String host, int port, int timeout) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(poolConfig, host, port, timeout);
    }

    public void set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }

    public String get(String key) {
        String value;
        try (Jedis jedis = jedisPool.getResource()) {
            value = jedis.get(key);
        }
        return value;
    }

    public void del(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key + "_50", key + "_100", key + "_200", key + "_500", key + "_1000", key + "_2000", key + "_0");
        }
    }

}
