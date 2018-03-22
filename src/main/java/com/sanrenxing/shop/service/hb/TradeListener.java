package com.sanrenxing.shop.service.hb;

/**
 * Created by liuyangkly on 15/10/27.
 */
public interface TradeListener {

    // 支付成功
    void onPayTradeSuccess(String outTradeNo, long beforeCall);

    // 支付处理中
    void onPayInProgress(String outTradeNo, long beforeCall);

    // 支付失败
    void onPayFailed(String outTradeNo, long beforeCall);

    // 建立连接异常
    void onConnectException(String outTradeNo, long beforeCall);

    // 报文上送异常
    void onSendException(String outTradeNo, long beforeCall);

    // 报文接收异常
    void onReceiveException(String outTradeNo, long beforeCall);
}
