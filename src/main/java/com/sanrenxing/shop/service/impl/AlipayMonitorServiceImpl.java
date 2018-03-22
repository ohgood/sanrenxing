package com.sanrenxing.shop.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.MonitorHeartbeatSynRequest;
import com.alipay.api.response.MonitorHeartbeatSynResponse;
import com.sanrenxing.shop.model.Alipay;
import com.sanrenxing.shop.model.builder.AlipayHeartbeatSynRequestBuilder;
import com.sanrenxing.shop.service.AlipayMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created on 18/3/2.
 * @author tony
 */
@Service
public class AlipayMonitorServiceImpl extends AbsAlipayService implements AlipayMonitorService {

    private Logger logger = LoggerFactory.getLogger(AlipayMonitorServiceImpl.class);

    @Autowired
    private Alipay alipay;

    private AlipayClient client;

    @PostConstruct
    public void init() {
        client = new DefaultAlipayClient(alipay.getMcloudApiDomain(), alipay.getAppid(), alipay.getPrivateKey(),
                "json", "utf-8", alipay.getSignType());

    }
    @Override
    public MonitorHeartbeatSynResponse heartbeatSyn(AlipayHeartbeatSynRequestBuilder builder) {
        validateBuilder(builder);
        MonitorHeartbeatSynRequest request = new MonitorHeartbeatSynRequest();
        request.putOtherTextParam("app_auth_token", builder.getAppAuthToken());
        request.setBizContent(builder.toJsonString());
        logger.info("heartbeat.sync bizContent:" + request.getBizContent());

        return (MonitorHeartbeatSynResponse) getResponse(client, request);
    }
}
