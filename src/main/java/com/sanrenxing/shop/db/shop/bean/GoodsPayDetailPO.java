package com.sanrenxing.shop.db.shop.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created on 2018/3/22.
 * @author tony
 */
@Data
public class GoodsPayDetailPO {

    private Integer id;

    private String goodsId;

    private String alipayGoodsId;

    private String goodsName;

    private Integer quantity;

    private Double price;

    private String goodsCategory;

    private String body;

    private Timestamp addTime;

}
