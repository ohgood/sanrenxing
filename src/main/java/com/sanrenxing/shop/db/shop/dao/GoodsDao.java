package com.sanrenxing.shop.db.shop.dao;

import com.sanrenxing.shop.db.shop.bean.GoodsDetailPO;
import com.sanrenxing.shop.db.shop.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2018/3/15.
 * @author tony
 */
@Component
public class GoodsDao {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 书籍查询接口
     * @return            查询到的书籍数据
     */
    public List<GoodsDetailPO> findAll() {
        return goodsMapper.findAll();
    }

    /**
     * 书籍创建接口
     * @param bookPO      书籍
     */
    public boolean createBook(GoodsDetailPO bookPO){
        return goodsMapper.createBook(bookPO) > 0;
    }

    /**
     * 查询热门物品接口
     * @return            查询到的书籍数据
     */
    public List<GoodsDetailPO> findHot() {
        return goodsMapper.findHot();
    }

    /**
     * 查询物品接口
     * @return            查询到的书籍数据
     */
    public GoodsDetailPO findOne(Integer id) {
        return goodsMapper.findOne(id);
    }

}
