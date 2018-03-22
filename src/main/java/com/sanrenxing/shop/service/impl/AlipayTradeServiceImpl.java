package com.sanrenxing.shop.service.impl;

import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.sanrenxing.shop.db.Alipay;
import com.sanrenxing.shop.model.TradeStatus;
import com.sanrenxing.shop.model.builder.AlipayTradePayRequestBuilder;
import com.sanrenxing.shop.model.builder.AlipayTradeQueryRequestBuilder;
import com.sanrenxing.shop.model.result.AlipayF2FPayResult;
import com.sanrenxing.shop.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuyangkly on 15/7/29.
 *
 *  一定要在创建AlipayTradeService之前调用Configs.init("xxxxxx");设置参数
 *
 */
@Service
public class AlipayTradeServiceImpl extends AbsAlipayTradeService {

    private Logger logger = LoggerFactory.getLogger(AlipayTradeServiceImpl.class);

    @Autowired
    public AlipayTradeServiceImpl(Alipay alipay) {
        client = new DefaultAlipayClient(alipay.getOpenApiDomain(), alipay.getAppid(), alipay.getPrivateKey(),
                "json", "utf-8", alipay.getAlipayPublicKey(), alipay.getSignType());
    }

    // 商户可以直接使用的pay方法
    @Override
    public AlipayF2FPayResult tradePay(AlipayTradePayRequestBuilder builder) {
        validateBuilder(builder);

        final String outTradeNo = builder.getOutTradeNo();

        AlipayTradePayRequest request = new AlipayTradePayRequest();
        // 设置平台参数
        request.setNotifyUrl(builder.getNotifyUrl());
        String appAuthToken = builder.getAppAuthToken();
        // todo 等支付宝sdk升级公共参数后使用如下方法
        // request.setAppAuthToken(appAuthToken);
        request.putOtherTextParam("app_auth_token", builder.getAppAuthToken());

        // 设置业务参数
        request.setBizContent(builder.toJsonString());
        logger.info("runner.pay bizContent:" + request.getBizContent());

        // 首先调用支付api
        AlipayTradePayResponse response = (AlipayTradePayResponse) getResponse(client, request);

        AlipayF2FPayResult result = new AlipayF2FPayResult(response);
        if (response != null && Constants.SUCCESS.equals(response.getCode())) {
            // 支付交易明确成功
            result.setTradeStatus(TradeStatus.SUCCESS);

        } else if (response != null && Constants.PAYING.equals(response.getCode())) {
            // 返回用户处理中，则轮询查询交易是否成功，如果查询超时，则调用撤销
            AlipayTradeQueryRequestBuilder queryBuiler = new AlipayTradeQueryRequestBuilder()
                                                            .setAppAuthToken(appAuthToken)
                                                            .setOutTradeNo(outTradeNo);
            AlipayTradeQueryResponse loopQueryResponse = loopQueryResult(queryBuiler);
            return checkQueryAndCancel(outTradeNo, appAuthToken, result, loopQueryResponse);

        } else if (tradeError(response)) {
            // 系统错误，则查询一次交易，如果交易没有支付成功，则调用撤销
            AlipayTradeQueryRequestBuilder queryBuiler = new AlipayTradeQueryRequestBuilder()
                                                            .setAppAuthToken(appAuthToken)
                                                            .setOutTradeNo(outTradeNo);
            AlipayTradeQueryResponse queryResponse = tradeQuery(queryBuiler);
            return checkQueryAndCancel(outTradeNo, appAuthToken, result, queryResponse);

        } else {
            // 其他情况表明该订单支付明确失败
            result.setTradeStatus(TradeStatus.FAILED);
        }

        return result;
    }
}
