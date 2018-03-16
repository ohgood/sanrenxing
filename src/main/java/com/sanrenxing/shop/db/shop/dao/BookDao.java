package com.sanrenxing.shop.db.shop.dao;

import com.sanrenxing.shop.db.shop.bean.BookPO;
import com.sanrenxing.shop.db.shop.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tony
 * @date 2018/3/15.
 * @description
 */
@Component
public class BookDao {

    @Autowired
    private BookMapper bookMapper;

    /**
     * 书籍查询接口
     * @return            查询到的书籍数据
     */
    public List<BookPO> findAll() {
        return bookMapper.findAll();
    }

    /**
     * 书籍创建接口
     * @param bookPO      书籍
     */
    public boolean createBook(BookPO bookPO){
        return bookMapper.createBook(bookPO) > 0;
    }
}
