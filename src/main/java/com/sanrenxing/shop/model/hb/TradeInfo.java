package com.sanrenxing.shop.model.hb;

/**
 * Created on 18/3/2.
 * @author tony
 */
public interface TradeInfo {
    // 获取交易状态
    public HbStatus getStatus();

    // 获取交易时间
    public double getTimeConsume();
}
