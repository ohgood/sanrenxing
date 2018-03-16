package com.sanrenxing.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created on 2017/7/13.
 * @author tony
 */
@EnableAsync
@EnableCaching
@SpringBootApplication
@PropertySource(value = "classpath:config.properties", ignoreResourceNotFound = true)
public class Bootstrap {

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

}
