package com.sanrenxing.shop.helper;

import com.sanrenxing.shop.db.admin.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Redis
 * Created on 2017/8/11.
 * @author tony
 */
public class RedisConnector {

    private JedisPool jedisPool;

    private UserMapper userMapper;

    private Jedis jedis = jedisPool.getResource();

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public RedisConnector(String host, int port, int timeout) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(poolConfig, host, port, timeout);
    }

    public void set(String key, String value) {
        jedis.set(key, value);
    }

    /**
     * 防止缓存穿透
     * @param key
     * @return
     */
    public String get(String key) {
        String value = jedis.get(key);
        if (StringUtils.isBlank(value)) {
            this.set(key, value);
            if (userMapper.findByUsername(key) == null) {
                jedis.expire(key, 60 * 5);
            }

        } else {
            return value;
        }
        return value;
    }

    /**
     * “提前”使用互斥锁(mutex key) 防止缓存击穿
     * @param key
     * @return
     */
    public String getNoBreakdown(String key) {
        String value = jedis.get(key);
        long time = jedis.ttl(key);
        if (time <= System.currentTimeMillis()) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    String keyMutex = "mutex:" + key;
                    if (jedis.setnx(keyMutex, "1") == 1) {
                        jedis.expire(keyMutex, 3 * 60);
                        jedis.set(key, userMapper.findByUsername(key).toString());
                        jedis.del(keyMutex);
                    }
                }
            });
        }
        return value;
    }

    public void del(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key + "_50", key + "_100", key + "_200", key + "_500", key + "_1000", key + "_2000", key + "_0");
        }
    }

}
