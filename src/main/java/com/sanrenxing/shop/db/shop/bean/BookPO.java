package com.sanrenxing.shop.db.shop.bean;

import com.sanrenxing.shop.db.annotation.Table;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Created on 2018/3/15.
 * @author tony
 */
@Data
@Table("book")
public class BookPO {

    private int id;

    private String name;

    private String description;

    private String picUrl;

    private Timestamp addTime;

    private Timestamp updateTime;
}
