package com.sanrenxing.shop.db.shop.dao;

import com.sanrenxing.shop.db.shop.bean.NewsPO;
import com.sanrenxing.shop.db.shop.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2018/4/9.
 * @author tony
 */
@Component
public class NewsDao {

    @Autowired
    private NewsMapper newsMapper;

    /**
     * 查询最新消息
     * @return  新消息
     */
    public List<NewsPO> getList() {
        return newsMapper.getList();
    }
}
