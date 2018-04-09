package com.sanrenxing.shop.service;

import com.sanrenxing.shop.db.shop.bean.NewsPO;

import java.util.List;

/**
 * Created on 2018/4/9.
 * @author tony
 */
public interface NewsService {

    /**
     * 查询最新消息
     * @return  新消息
     */
    List<NewsPO> getList();
}
