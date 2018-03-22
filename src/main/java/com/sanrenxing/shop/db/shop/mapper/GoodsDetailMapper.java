package com.sanrenxing.shop.db.shop.mapper;

import com.sanrenxing.shop.db.shop.bean.GoodsDetailPO;
import org.apache.ibatis.annotations.Select;

/**
 * Created on 2018/3/22.
 * @author tony
 */
public interface GoodsDetailMapper {

    void create(GoodsDetailPO goodsDetailPO);
}
