package com.sanrenxing.shop.db.shop.mapper;

import com.sanrenxing.shop.db.shop.SqlProvider;
import com.sanrenxing.shop.db.shop.bean.BookPO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created on 2018/3/15
 * @author tony
 */
public interface BookMapper {

    /**
     * 书籍查询接口
     * @return            查询到的书籍数据
     */
    @Select("SELECT id, name, pic_url, description, add_time, update_time FROM book WHERE state = 0 ORDER BY add_time")
    List<BookPO> findAll();

    /**
     * 书籍创建接口
     * @param bookPO      书籍
     */
    @InsertProvider(type = SqlProvider.class, method = "insert")
    int createBook(BookPO bookPO);
}
