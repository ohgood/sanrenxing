package com.sanrenxing.shop.db.shop.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created on 2018/4/9.
 * @author wukaitong
 */
@Data
public class NewsPO {

    private Integer id;

    private Integer goodsId;

    private String picUrl;

    private String context;

    private Timestamp addTime;
}
