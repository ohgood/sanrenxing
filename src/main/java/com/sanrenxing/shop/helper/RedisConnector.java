package com.sanrenxing.shop.helper;

import com.sanrenxing.shop.db.admin.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
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

    private UserMapper userMapper;

    public RedisConnector(String host, int port, int timeout) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(poolConfig, host, port, timeout);
    }

    public void set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }

    /**
     * 防止缓存击穿
     * @param key
     * @return
     */
    public String get(String key) {
        String value;
        try (Jedis jedis = jedisPool.getResource()) {
            value = jedis.get(key);
            if (StringUtils.isBlank(value)) {
                this.set(key, value);
                if (userMapper.findByUsername(key) == null) {
                    jedis.expire(key, 60 * 5);
                }

            } else {
                return value;
            }
        }
        return value;
    }

    public void del(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key + "_50", key + "_100", key + "_200", key + "_500", key + "_1000", key + "_2000", key + "_0");
        }
    }

}
