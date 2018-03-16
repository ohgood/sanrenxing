package com.sanrenxing.shop.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created on 16/5/19
 * @author yasenia
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static Properties loadProperties(String path) {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            logger.error("配置文件加载失败 --- path: " + path, e);
        }
        return properties;
    }

    public static InputStream loadAsInputStream(String path) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

    public static boolean notNull(Object... objects) {
        for (Object o : objects) {
            if (null == o) return false;
        }
        return true;
    }
}
