package com.sanrenxing.shop.db.shop.dao;

import com.sanrenxing.shop.db.shop.bean.OrderPO;
import com.sanrenxing.shop.db.shop.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created on 2018/4/10.
 * @author tony
 */
@Component
public class OrderDao {

    @Autowired
    private OrderMapper orderMapper;

    public OrderPO create(OrderPO orderPO) {
        orderMapper.create(orderPO);
        return orderPO;
    }
}
