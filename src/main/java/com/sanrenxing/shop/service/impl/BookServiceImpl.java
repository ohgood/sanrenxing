package com.sanrenxing.shop.service.impl;

import com.sanrenxing.shop.controller.dto.BookDTO;
import com.sanrenxing.shop.db.shop.bean.BookPO;
import com.sanrenxing.shop.db.shop.dao.BookDao;
import com.sanrenxing.shop.service.BookService;
import com.sanrenxing.shop.util.BeanUtil;
import com.sanrenxing.shop.util.oss.OSSHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * Created on 2018/3/15
 * @author tony
 */
@Service
public class BookServiceImpl implements BookService{

    private final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookDao bookDao;

    @Autowired
    private OSSHelper ossHelper;

    /**
     * 书籍查询接口
     * @return            查询到的书籍数据
     */
    @Override
    public List<BookDTO> findAll() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<BookPO> bookPOs = bookDao.findAll();
        //bookPOs.forEach(bookPO -> bookPO.setPicUrl(ossHelper.publicUrl(bookPO.getPicUrl())));
        return BeanUtil.copyTo(bookPOs, BookDTO.class);
    }

    /**
     * 书籍创建接口
     * @param name           书名
     * @param description    描述
     * @param multipartFile  文件
     */
    @Override
    public boolean createBook(String name, String description, MultipartFile multipartFile){
        BookPO bookPO = new BookPO();
        bookPO.setName(name);
        bookPO.setDescription(description);
        String picName = multipartFile.getOriginalFilename();
        try {
            ossHelper.putObject(picName, multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件上传失败！！！ name: " + name);
        }
        bookPO.setPicUrl(picName);
        return bookDao.createBook(bookPO);
    }
}
