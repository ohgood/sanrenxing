package com.sanrenxing.shop.service.impl;

import com.sanrenxing.shop.db.shop.bean.GoodsDetailPO;
import com.sanrenxing.shop.db.shop.bean.OrderPO;
import com.sanrenxing.shop.db.shop.dao.GoodsDao;
import com.sanrenxing.shop.db.shop.dao.OrderDao;
import com.sanrenxing.shop.rest.SRXResponse;
import com.sanrenxing.shop.service.OrderService;
import com.sanrenxing.shop.util.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2018/4/10.
 * @author tony
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public SRXResponse create(String operator, Integer amount, Double sum, String receiver,
                              String iphone, String address, Integer goodId) {
        synchronized (this) {
            GoodsDetailPO goodsDetailPO = goodsDao.findOne(goodId);
            if (goodsDetailPO.getAmount() >= amount) {
                //减少库存
                goodsDao.reduceStock(goodId, goodsDetailPO.getAmount() - amount);
                OrderPO orderPO = new OrderPO();
                orderPO.setOrderNo(OrderNoUtil.makeOrderNum());
                orderPO.setOperator(operator);
                orderPO.setState(0);
                orderPO.setAmount(amount);
                orderPO.setSum(sum);
                orderPO.setSaleArea(goodsDetailPO.getSaleArea());
                orderPO.setReceiver(receiver);
                orderPO.setIphone(iphone);
                orderPO.setAddress(address);
                orderPO.setGoodsName(goodsDetailPO.getName());
                orderPO.setGoodsType(goodsDetailPO.getType());
                return new SRXResponse(SRXResponse.Status.SUCCESS).result(orderDao.create(orderPO));
            } else {
                return new SRXResponse(SRXResponse.Status.FAILURE).message("下单失败");
            }
        }

    }
}
