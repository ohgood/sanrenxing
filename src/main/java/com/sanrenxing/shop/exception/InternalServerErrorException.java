package com.sanrenxing.shop.exception;

/**
 * 服务器内部错误
 *
 * Created on 2017/7/13.
 *
 * @author tony
 */
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
