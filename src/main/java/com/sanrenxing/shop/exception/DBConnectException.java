package com.sanrenxing.shop.exception;

/**
 * 数据库连接异常
 * Created on 2017/7/13.
 * @author tony
 */
public class DBConnectException extends RuntimeException {

    public DBConnectException(String message) {
        super(message);
    }

    public DBConnectException(String message, Throwable cause) {
        super(message, cause);
    }

}
