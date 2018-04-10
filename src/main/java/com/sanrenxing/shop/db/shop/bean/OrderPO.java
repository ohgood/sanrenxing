package com.sanrenxing.shop.db.shop.bean;

import com.sanrenxing.shop.db.annotation.Table;
import lombok.Data;

/**
 * Created on 2018/4/10.
 * @author tony
 */
@Data
@Table("order")
public class OrderPO {

    private Integer id;

    private String orderNo;

    private String operator;

    private Integer state;

    private Integer amount;

    private Double sum;

    private String saleArea;

    private String receiver;

    private String iphone;

    private String address;

    private String goodsName;

    private String goodsType;

}
