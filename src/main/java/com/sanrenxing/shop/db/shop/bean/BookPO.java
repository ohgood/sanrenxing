package com.sanrenxing.shop.db.shop.bean;

import com.sanrenxing.shop.db.annotation.Table;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author tony
 * @date 2018/3/15.
 * @descrition
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
