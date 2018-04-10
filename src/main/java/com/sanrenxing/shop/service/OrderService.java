package com.sanrenxing.shop.service;

import com.sanrenxing.shop.rest.SRXResponse;

/**
 * Created on 2018/4/10.
 * @author wukaitong
 */
public interface OrderService {


    SRXResponse create(String operator, Integer amount, Double sum, String receiver,
                       String iphone, String address, Integer goodId);
}
