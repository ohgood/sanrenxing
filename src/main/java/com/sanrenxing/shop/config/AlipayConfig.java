package com.sanrenxing.shop.config;

import com.sanrenxing.shop.model.Alipay;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2018/3/22.
 * @author tony
 */
@Configuration
public class AlipayConfig {

    @Bean
    @ConfigurationProperties(prefix = "Alipay")
    public Alipay alipay() {
        return new Alipay();
    }

}
