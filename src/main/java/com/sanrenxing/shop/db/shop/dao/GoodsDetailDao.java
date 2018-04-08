package com.sanrenxing.shop.db.shop.dao;

import com.sanrenxing.shop.db.shop.bean.GoodsPayDetailPO;
import com.sanrenxing.shop.db.shop.mapper.GoodsDetailMapper;

/**
 * Created on 2018/3/22.
 * @author tony
 */
public class GoodsDetailDao {

    private GoodsDetailMapper goodsDetailMapper;

    public void create(GoodsPayDetailPO goodsDetailPO) {
        goodsDetailMapper.create(goodsDetailPO);
    }
}
