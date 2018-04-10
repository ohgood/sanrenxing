package com.sanrenxing.shop.controller.business;

import com.sanrenxing.shop.rest.SRXResponse;
import com.sanrenxing.shop.service.OrderService;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
    public SRXResponse create(@NotEmpty @RequestParam("operator") String operator,
                              @NotNull @RequestParam("amount") Integer amount,
                              @NotNull @RequestParam("sum") Double sum,
                              @NotEmpty @RequestParam("receiver") String receiver,
                              @NotEmpty
                              @Pattern(regexp = "^((19[0-9])|(13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$", message = "请输入正确的手机号！")
                              @RequestParam("iphone") String iphone,
                              @NotEmpty  @RequestParam("address") String address,
                              @NotNull @RequestParam("goodId") Integer goodId) {

        return orderService.create(operator, amount, sum, receiver, iphone, address, goodId);
    }

}
