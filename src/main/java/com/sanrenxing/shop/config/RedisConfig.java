package com.sanrenxing.shop.config;

import com.sanrenxing.shop.helper.RedisConnector;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2018/3/23.
 * @author tony
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis", ignoreUnknownFields = false)
public class RedisConfig {
    private String host;
    private int port;
    private int timeout;
    // private String auth;

    @Bean
    public RedisConnector redisConnector() {
        return new RedisConnector(host, port, timeout);
    }
}
