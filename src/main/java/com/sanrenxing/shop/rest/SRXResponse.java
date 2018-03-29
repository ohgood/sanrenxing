package com.sanrenxing.shop.rest;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 定义各种返回码和提示信息
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SRXResponse {

    private int code;
    private String message;
    private Object result;

    public SRXResponse(Status status) {
        this.code = status.code;
        this.message = status.message;
    }

    public SRXResponse(Status status, Object result) {
        this.code = status.code;
        this.message = status.message;
        this.result = result;
    }

    public SRXResponse message(String message) {
        this.message = message;
        return this;
    }

    public SRXResponse result(Object result) {
        this.result = result;
        return this;
    }

    public enum Status {
        // 公共部分
        SUCCESS(0, "请求成功"),
        REQUEST_PARAMETER_ERROR(100002, "请求参数错误"),
        SIGN_ERROR(100003, "sign验证错误"),
        SYSTEM_ERROR(100098, "系统异常"),
        UNKNOWN_ERROR(100099, "未知异常"),
        FAILURE(100006, "操作执行失败"),
        UNAUTHORITY(100007, "无访问权限"),
        ERROR_403(10403, "非法请求"),
        ERROR_404(10404, "访问的路径不存在"),
        LOGOUT(10004, "已退出登录"),

        // 用户
        USER_EXISTS(200001, "账户已存在"),
        LOGIN_FAIL(200002, "用户名/密码错误"),
        USER_NOT_EXISTS(200003, "用户不存在"),
        USER_LOCKED(200004, "用户被锁定");


        // 实名认证 5开头的已经用过了 在web里面

        private int code;
        private String message;

        Status(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }


}