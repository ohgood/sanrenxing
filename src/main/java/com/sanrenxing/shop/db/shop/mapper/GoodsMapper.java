package com.sanrenxing.shop.db.shop.mapper;

import com.sanrenxing.shop.db.shop.SqlProvider;
import com.sanrenxing.shop.db.shop.bean.GoodsPO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created on 2018/3/15
 * @author tony
 */
public interface GoodsMapper {

    /**
     * 书籍查询接口
     * @return            查询到的书籍数据
     */
    @Select("SELECT id, name, pic_url, description, add_time, update_time FROM goods WHERE state = 0 AND type = 1 AND is_hot =0 ORDER BY add_time")
    List<GoodsPO> findAll();

    /**
     * 书籍创建接口
     * @param bookPO      书籍
     */
    @InsertProvider(type = SqlProvider.class, method = "insert")
    int createBook(GoodsPO bookPO);

    /**
     * 查询热门物品接口
     * @return            查询到的书籍数据
     */
    @Select("SELECT id, name, pic_url, description, add_time, update_time FROM goods WHERE state = 0 AND is_hot = 1 ORDER BY add_time")
    List<GoodsPO> findHot();
}
