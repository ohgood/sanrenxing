package com.sanrenxing.shop.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 2018/4/10.
 * @author tony
 */
public class OrderNoUtil {
    /**
     * 订单号生成计数器
     */
    private static long orderNumCount = 0L;
    /**
     * 每毫秒生成订单号数量最大值
     */
    private static int maxPerMSECSize=1000;
    /**
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展
     */
    public static String makeOrderNum() {
        // 最终生成的订单号
        String finOrderNum = "";

        // 取系统当前时间作为订单号变量前半部分，精确到毫秒
        long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
        if (orderNumCount >= maxPerMSECSize) {
            orderNumCount = 0L;
        }
        //组装订单号
        String countStr = maxPerMSECSize +orderNumCount+"";
        finOrderNum = nowLong+countStr.substring(1);
        return finOrderNum;
    }
}
