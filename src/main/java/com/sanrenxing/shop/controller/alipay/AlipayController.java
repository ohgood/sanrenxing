package com.sanrenxing.shop.controller.alipay;

import com.sanrenxing.shop.rest.SRXResponse;
import com.sanrenxing.shop.service.AlipayCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2018/3/22.
 * @author tony
 */
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    @Autowired
    private AlipayCommonService alipayCommonService;

    @RequestMapping("/testTradePrecreate")
    public SRXResponse testTradePrecreate() throws Exception {
        alipayCommonService.test_trade_precreate();
        return new SRXResponse(SRXResponse.Status.SUCCESS);
    }

}
