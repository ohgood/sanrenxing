package com.sanrenxing.shop.db.shop.mapper;

import com.sanrenxing.shop.db.shop.bean.NewsPO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created on 2018/4/9.
 * @author tony
 */
public interface NewsMapper {

    /**
     * 查询最新消息
     * @return  新消息
     */
    @Select("SELECT id, goods_id, context, pic_url, add_time FROM news ORDER BY add_time DESC LIMIT 3")
    List<NewsPO> getList();
}
