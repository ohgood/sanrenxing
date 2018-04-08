package com.sanrenxing.shop.controller.business;

import com.sanrenxing.shop.db.shop.bean.GoodsPO;
import com.sanrenxing.shop.rest.SRXResponse;
import com.sanrenxing.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created on 2018/8/8
 * @author tony
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 书籍查询接口
     * @return            查询到的书籍数据
     */
    @RequestMapping("/findAll")
    public SRXResponse findAll() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return new SRXResponse(SRXResponse.Status.SUCCESS).result(goodsService.findAll());
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
        goodsService.createBook(name, description, multipartFile);
        return new SRXResponse(SRXResponse.Status.SUCCESS);
    }

    /**
     * 查询热门物品接口
     * @return            查询到的书籍数据
     */
    @RequestMapping(value = "/findHot", method = RequestMethod.GET)
    public SRXResponse findHot() {
        return new SRXResponse(SRXResponse.Status.SUCCESS).result(goodsService.findHot());
    }
}
