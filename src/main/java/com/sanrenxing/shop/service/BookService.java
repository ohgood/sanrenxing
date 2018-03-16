package com.sanrenxing.shop.service;

import com.sanrenxing.shop.controller.dto.BookDTO;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created on 2018/3/15
 * @author tony
 */
public interface BookService {

    /**
     * 书籍查询接口
     * @return            查询到的书籍数据
     */
    List<BookDTO> findAll() throws IllegalAccessException, InstantiationException, InvocationTargetException;

    /**
     * 书籍创建接口
     * @param name           书名
     * @param description    描述
     * @param multipartFile  文件
     */
   boolean createBook(String name, String description, MultipartFile multipartFile);
}
