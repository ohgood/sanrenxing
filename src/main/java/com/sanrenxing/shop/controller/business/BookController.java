package com.sanrenxing.shop.controller.business;

import com.sanrenxing.shop.rest.SRXResponse;
import com.sanrenxing.shop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;

/**
 * Created on 2018/8/8
 * @author tony
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 书籍查询接口
     * @return            查询到的书籍数据
     */
    @RequestMapping("/findAll")
    public SRXResponse findAll() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return new SRXResponse(SRXResponse.Status.SUCCESS).result(bookService.findAll());
    }

    /**
     * 书籍创建接口
     * @param name           书名
     * @param description    描述
     * @param multipartFile  文件
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public SRXResponse createBook(@RequestParam("name") String name,
                                  @RequestParam("description") String description,
                                  @RequestParam("file") MultipartFile multipartFile){
        bookService.createBook(name, description, multipartFile);
        return new SRXResponse(SRXResponse.Status.SUCCESS);
    }

}
