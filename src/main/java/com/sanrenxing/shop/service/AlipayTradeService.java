package com.sanrenxing.shop.service;

import com.sanrenxing.shop.model.builder.AlipayTradePayRequestBuilder;
import com.sanrenxing.shop.model.builder.AlipayTradePrecreateRequestBuilder;
import com.sanrenxing.shop.model.builder.AlipayTradeQueryRequestBuilder;
import com.sanrenxing.shop.model.builder.AlipayTradeRefundRequestBuilder;
import com.sanrenxing.shop.model.result.AlipayF2FPayResult;
import com.sanrenxing.shop.model.result.AlipayF2FPrecreateResult;
import com.sanrenxing.shop.model.result.AlipayF2FQueryResult;
import com.sanrenxing.shop.model.result.AlipayF2FRefundResult;

/**
 * Created on 18/3/2.
 * @author tony
 */
public interface AlipayTradeService {

    // 当面付2.0流程支付
    AlipayF2FPayResult tradePay(AlipayTradePayRequestBuilder builder);

    // 当面付2.0消费查询
    AlipayF2FQueryResult queryTradeResult(AlipayTradeQueryRequestBuilder builder);

    // 当面付2.0消费退款
    AlipayF2FRefundResult tradeRefund(AlipayTradeRefundRequestBuilder builder);

    // 当面付2.0预下单(生成二维码)
    AlipayF2FPrecreateResult tradePrecreate(AlipayTradePrecreateRequestBuilder builder);
}
