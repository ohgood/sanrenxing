package com.sanrenxing.shop.config;

import com.sanrenxing.shop.util.OSSHelper;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/10/31.
 * @author tony
 */
@Configuration
@ConfigurationProperties(prefix = "qiniuyun.oss", ignoreUnknownFields = false)
@Component
@Data
public class OSSConfig {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    @Bean
    public OSSHelper ossHelper() {
        return new OSSHelper(accessKeyId, accessKeySecret, bucketName);
    }

}
