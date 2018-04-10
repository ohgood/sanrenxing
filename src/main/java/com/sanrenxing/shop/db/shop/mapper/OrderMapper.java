package com.sanrenxing.shop.db.shop.mapper;

import com.sanrenxing.shop.db.shop.SqlProvider;
import com.sanrenxing.shop.db.shop.bean.OrderPO;
import org.apache.ibatis.annotations.InsertProvider;

/**
 * Created on 2018/4/10.
 * @author tony
 */
public interface OrderMapper {

    @InsertProvider(type = SqlProvider.class, method = "insert")
    void create(OrderPO orderPO);
}
