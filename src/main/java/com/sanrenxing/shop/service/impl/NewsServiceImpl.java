package com.sanrenxing.shop.service.impl;

import com.sanrenxing.shop.db.shop.bean.NewsPO;
import com.sanrenxing.shop.db.shop.dao.NewsDao;
import com.sanrenxing.shop.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2018/4/9.
 * @author tony
 */
@Service
public class NewsServiceImpl implements NewsService{

    @Autowired
    private NewsDao newsDao;

    /**
     * 查询最新消息
     * @return  新消息
     */
    @Override
    public List<NewsPO> getList() {
        return newsDao.getList();
    }
}
