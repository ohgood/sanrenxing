package com.sanrenxing.shop.controller.business;

import com.sanrenxing.shop.rest.SRXResponse;
import com.sanrenxing.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2018/4/10.
 * @author tony
 */
@RestController
@RequestMapping(value = "/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public SRXResponse create(@RequestParam("operator") String operator,
                              @RequestParam("amount") Integer amount,
                              @RequestParam("sum") Double sum,
                              @RequestParam("receiver") String receiver,
                              @RequestParam("iphone") String iphone,
                              @RequestParam("address") String address,
                              @RequestParam("goodId") Integer goodId) {

        return orderService.create(operator, amount, sum, receiver, iphone, address, goodId);
    }

}
